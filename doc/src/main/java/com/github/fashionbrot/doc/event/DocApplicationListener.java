package com.github.fashionbrot.doc.event;


import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.DocConfigurationProperties;
import com.github.fashionbrot.doc.consts.MarsDocConst;
import com.github.fashionbrot.doc.enums.ParameterizedTypeEnum;
import com.github.fashionbrot.doc.enums.RequestTypeEnum;
import com.github.fashionbrot.doc.type.DocParameterizedType;
import com.github.fashionbrot.doc.annotation.Api;
import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.enums.ClassTypeEnum;
import com.github.fashionbrot.doc.util.*;
import com.github.fashionbrot.doc.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


import java.lang.reflect.*;
import java.util.*;

@Slf4j
public class DocApplicationListener implements ApplicationListener<ContextRefreshedEvent>, BeanFactoryAware, EnvironmentAware {

    public static final String BEAN_NAME = "docApplicationListener";

    private static final String REQUEST_BEAN_NAME = "requestMappingHandlerMapping";


    private DocConfigurationProperties docConfigurationProperties;

    private Set<Class> loadClassSet = new HashSet<>();

    public Class loadClass(String className){
        Optional<Class> first = loadClassSet.stream().filter(m -> m.getSimpleName().equals(className)).findFirst();
        Class clazz = null;
        if (first.isPresent()){
            return first.get();
        }else {
            try {
                clazz = Class.forName(className);
                loadClassSet.add(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return clazz;
    }



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        if (!applicationContext.containsBean(REQUEST_BEAN_NAME)) {
            return;
        }


        DocVo docVo = DocVo.builder().build();

        List<LinkVo> requestVoList = new ArrayList<>();
        List<LinkVo> responseVoList = new ArrayList<>();
        List<ClassVo> classVoList = new ArrayList<>();

        RequestMappingHandlerMapping requestMapping = applicationContext.getBean(REQUEST_BEAN_NAME, RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> infoMap = requestMapping.getHandlerMethods();

        if (infoMap != null) {
            for (Map.Entry<RequestMappingInfo, HandlerMethod> map : infoMap.entrySet()) {


                HandlerMethod value = map.getValue();
                Method method = value.getMethod();
                if ("org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController".equals(method.getDeclaringClass().getName())) {
                    continue;
                }

                //接口类名
                Class<?> declaringClass = method.getDeclaringClass();

                String classId = declaringClass.getName();
                String methodId = declaringClass.getName()+"#"+method.getName();

                String className = declaringClass.getName();
                int priority = 0;
                Api api = declaringClass.getDeclaredAnnotation(Api.class);
                if (api != null) {
                    if (api.hidden()) {
                        continue;
                    }
                    className = api.value();
                    priority = api.priority();
                }



                List<MethodVo> methodVoList = RequestMappingUtil.getRequestMapping(method);
                if (ObjectUtil.isNotEmpty(methodVoList)) {

                    String methodDescription = method.getName();
                    int methodPriority = 0;
                    ApiOperation apiOperation = method.getDeclaredAnnotation(ApiOperation.class);
                    if (apiOperation != null) {
                        methodDescription = apiOperation.value();
                        methodPriority = apiOperation.priority();
                    }

                    for (MethodVo vo : methodVoList) {
                        vo.setMethodId(methodId);
                        vo.setDescription(methodDescription);
                        vo.setPriority(methodPriority);
                    }
                }

                long reqCount = requestVoList.stream().filter(m -> m.getMethodId().equals(methodId)).count();
                if (reqCount == 0) {
                    requestVoList.add(LinkVo.builder()
                            .methodId(methodId)
                            .list(getRequest(method))
                            .build());
                }

                long respCount = responseVoList.stream().filter(m -> m.getMethodId().equals(methodId)).count();
                if (respCount == 0) {
                    responseVoList.add(LinkVo.builder()
                            .methodId(methodId)
                            .list(getResponse(method))
                            .build());
                }


                ClassVo classVo = ClassVo.builder()
                        .classId(classId)
                        .description(className)
                        .priority(priority)
                        .methodList(methodVoList)
                        .build();

                classVoList.add(classVo);

            }
        }
        docVo.setClassList(classVoList);
        docVo.setRequestList(requestVoList);
        docVo.setResponseList(responseVoList);


        System.out.println(JSON.toJSONString(docVo));

    }







    private List<ParameterVo> getResponse(Method method) {


        List<ParameterVo> list = new ArrayList<>();
        Class<?> returnType = method.getReturnType();
        if (returnType != Void.class) {

            MethodTypeVo methodTypeRoot = getMethodTypeVo(method);

            if (ClassTypeEnum.checkClass(returnType.getTypeName())) {
                String description = "";
                boolean required = false;
                String example = "";
                String dataType = returnType.getTypeName();
                String name = returnType.getSimpleName();
                ApiModelProperty apiModelProperty = returnType.getDeclaredAnnotation(ApiModelProperty.class);
                if (apiModelProperty != null) {
                    description = apiModelProperty.value();
                    required = apiModelProperty.required();
                    example = apiModelProperty.example();
                    dataType = apiModelProperty.dataType();
                }
                ParameterVo build = ParameterVo.builder()
                        .name(name)
                        .description(description)
                        .dataType(dataType)
                        .required(required)
                        .example(example)
                        .build();
                list.add(build);
            } else {
                List<ParameterVo> parameterVos = fieldConvertParameter(returnType, methodTypeRoot.getChild(), "");
                if (ObjectUtil.isNotEmpty(parameterVos)) {
                    list.addAll(parameterVos);
                }
            }
        }
        return list;
    }

    private MethodTypeVo getMethodTypeVo(Method method){
        String methodId = MethodUtil.getMethodId(method);

        Type genericReturnType = method.getGenericReturnType();
        TypeVariable<? extends Class<?>>[] typeParameters = method.getReturnType().getTypeParameters();

        List<MethodTypeVo> methodTypeVoList= new ArrayList<>();

        if (ObjectUtil.isNotEmpty(typeParameters)){
            Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
            for (int i = 0; i < typeParameters.length; i++) {
                Type  classType = actualTypeArguments[i];
                Type  genType = typeParameters[i];

                Class typeClass = typeConvertClass(classType);

                MethodTypeVo build = MethodTypeVo.builder()
                        .methodId(methodId)
                        .typeClass(typeClass)
                        .typeClassStr(classType.getTypeName())
                        .typeName(genType.getTypeName())
                        .build();


                if (!ClassTypeEnum.checkClass(classType.getTypeName())){

                    if (typeClass.isArray()){

                        DocParameterizedType parameterizedType = new DocParameterizedType(List.class, new Class[]{typeClass.getComponentType()},null);
                        List<MethodTypeVo> childList = getMethodTypeList(parameterizedType, genType, methodId);

                        build.setChild(childList);
                    }else {

                        List<MethodTypeVo> childList = getMethodTypeList(classType, genType, methodId);
                        build.setChild(childList);

                    }

                }
                methodTypeVoList.add(build);
            }
        }
        MethodTypeVo root = MethodTypeVo.builder()
                .methodId(methodId)
                .typeClass(typeConvertClass(genericReturnType))
                .typeName(genericReturnType.getTypeName())
                .typeClassStr(MarsDocConst.TYPE_CLASS_ROOT)
                .child(methodTypeVoList)
                .build();
        return root;
    }



    public List<MethodTypeVo> getMethodTypeList(Type classType,Type  genType,String methodId){
        List<MethodTypeVo> list=new ArrayList<>();


        if (!ParameterizedTypeEnum.isParameterizedType(classType.getClass().getName())  ){
            return list;
        }
//        TypeVariable<? extends Class<?>>[] typeVariables = ((ParameterizedTypeImpl) classType).getRawType().getTypeParameters();
        TypeVariable<? extends Class<?>>[] typeVariables = getTypeVariable(classType);

//        TypeVariable<?>[] typeParameters = ((ParameterizedType) classType).getRawType().getClass().getTypeParameters();
//        TypeVariable<?>[] typeVariables = ((TypeVariable) genType).getGenericDeclaration().getTypeParameters();
//        TypeVariable<?>[] typeParameters1 = genType.getClass().getTypeParameters();
        Type[] actualTypeArguments = ((ParameterizedType) classType).getActualTypeArguments();
        if (ObjectUtil.isNotEmpty(typeVariables)){
            for (int i = 0; i < typeVariables.length; i++) {
                Type typeVariable = typeVariables[i];
                Type type = actualTypeArguments[i];

                Class typeClass = typeConvertClass(type);

                MethodTypeVo build = MethodTypeVo.builder()
                        .methodId(methodId)
                        .typeClassStr(type.getTypeName())
                        .typeClass(typeClass)
                        .typeName(typeVariable.getTypeName())
                        .build();

                if (!ClassTypeEnum.checkClass(typeClass.getTypeName())){
                    if (typeClass.isArray()){
                        DocParameterizedType parameterizedType = new DocParameterizedType(List.class, new Class[]{typeClass.getComponentType()},null);
                        List<MethodTypeVo> childList = getMethodTypeList(parameterizedType, genType, methodId);
                        build.setChild(childList);
                    }else {
                        build.setChild(getMethodTypeList(type, typeVariable, methodId));
                    }
                }
                list.add(build);
            }
        }
        return list;
    }

    public TypeVariable[] getTypeVariable(Type type){
        Class typeClass = null;
        if(type instanceof  Class){
            typeClass = (Class) type;
        }else if (type instanceof ParameterizedType){
            typeClass = (Class) ((ParameterizedType) type).getRawType();
        }
        if (typeClass!=null){
            return typeClass.getTypeParameters();
        }
        return null;
    }

    private Class typeConvertClass(Type type) {
        Class typeClass = null;
        if(type instanceof  Class){
            typeClass = (Class) type;
        }else if (type instanceof ParameterizedType){
            typeClass = (Class) ((ParameterizedType) type).getRawType();
        }
        return typeClass;
    }




    private List<ParameterVo> getRequest(Method method) {
        Parameter[] parameters = method.getParameters();


        List<ParameterVo> parameterVoList = new ArrayList<>();

        if (ObjectUtil.isNotEmpty(parameters)) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];

                Class<?> classType = parameter.getType();
                String parameterName = parameter.getName();


                if (ClassTypeEnum.checkClass(classType.getName())) {


                    Class<?> type = parameter.getType();
                    String parameterDescription = "";
                    boolean required = false;
                    ApiModelProperty apiModelProperty = parameter.getDeclaredAnnotation(ApiModelProperty.class);
                    if (apiModelProperty != null) {
                        parameterDescription = apiModelProperty.value();
                        required = apiModelProperty.required();
                        if (apiModelProperty.hidden()) {
                            continue;
                        }
                    }

                    parameterVoList.add(ParameterVo.builder()
                            .name(parameterName)
                            .requestType(RequestTypeEnum.QUERY.name())
                            .description(parameterDescription)
                            .required(required)
                            .dataType(type.getSimpleName())
                            .build());

                } else {

                    RequestBody requestBody = parameter.getDeclaredAnnotation(RequestBody.class);
                    String requestType = requestBody != null ? RequestTypeEnum.BODY.name() : RequestTypeEnum.QUERY.name();
                    ParameterVo req = null;
                    if (requestBody != null) {
                        String description = parameterName;
                        ApiModel apiModel = classType.getDeclaredAnnotation(ApiModel.class);
                        if (apiModel != null) {
                            description = apiModel.value();
                        }

                        req = ParameterVo.builder()
                                .name(parameterName)
                                .requestType(requestType)
                                .required(true)
                                .description(description)
                                .build();
                    }
                    List<ParameterVo> parameterVos = fieldConvertParameter(classType,null, requestType);
                    if (req != null) {
                        req.setChild(parameterVos);
                        parameterVoList.add(req);
                    } else {
                        if (ObjectUtil.isNotEmpty(parameterVos)) {
                            parameterVoList.addAll(parameterVos);
                        }
                    }

                }

            }
        }
        return parameterVoList;
    }

    public List<ParameterVo> fieldConvertParameter(Class clazz,List<MethodTypeVo> methodTypeList, String requestType) {

        Field[] declaredFields = clazz.getDeclaredFields();
        if (ObjectUtil.isNotEmpty(declaredFields)) {
            List<ParameterVo> parameterVoList = new ArrayList<>();
            for (Field field : declaredFields) {
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }

                field.setAccessible(true);
                String fieldName = field.getName();

                String fieldDescription = "";
                boolean required = false;
                ApiModelProperty apiModelProperty = field.getDeclaredAnnotation(ApiModelProperty.class);
                if (apiModelProperty != null) {
                    fieldDescription = apiModelProperty.value();
                    required = apiModelProperty.required();
                    if (apiModelProperty.hidden()) {
                        continue;
                    }
                }

                ParameterVo build = ParameterVo.builder()
                        .name(fieldName)
                        .requestType(requestType)
                        .description(fieldDescription)
                        .required(required)
                        .dataType(field.getType().getSimpleName())
                        .build();

                if (!ClassTypeEnum.checkClass(field.getGenericType().getTypeName())) {
                    if (ObjectUtil.isNotEmpty(methodTypeList)){
                        Optional<MethodTypeVo> first = methodTypeList.stream().filter(m -> m.getTypeName().equals(field.getGenericType().getTypeName())).findFirst();
                        if (first.isPresent()){
                            MethodTypeVo methodType = first.get();
                            build.setDataType(methodType.getTypeClassStr());


                            if (JavaClassValidateUtil.isCollection(methodType.getTypeClass()) ) {
                                build.setCollection(1);
                                if (ObjectUtil.isNotEmpty(methodType.getChild())) {
                                    MethodTypeVo childMethodType = methodType.getChild().get(0);
                                    build.setChild(fieldConvertParameter(childMethodType.getTypeClass(), childMethodType.getChild(), requestType));
                                }
                            }else if (JavaClassValidateUtil.isArray(methodType.getTypeClass())){
                                build.setCollection(1);

                                build.setChild(fieldConvertParameter(methodType.getTypeClass().getComponentType(), methodType.getChild(), requestType));
                            }else{
                                build.setCollection(0);
                                if (ObjectUtil.isNotEmpty(methodType.getChild())){
                                    build.setChild(fieldConvertParameter(methodType.getTypeClass(),methodType.getChild(), requestType));
                                }
                            }

                        }
                    }
                }
                parameterVoList.add(build);
            }
            return parameterVoList;
        }
        return null;
    }




    @Override
    public void setEnvironment(Environment environment) {

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SingletonBeanRegistry beanRegistry = null;
        if (beanFactory instanceof SingletonBeanRegistry) {
            beanRegistry = (SingletonBeanRegistry) beanFactory;
        } else if (beanFactory instanceof AbstractApplicationContext) {
            beanRegistry = ((AbstractApplicationContext) beanFactory).getBeanFactory();
        }
        if (beanFactory.containsBean(DocConfigurationProperties.BEAN_NAME)) {
            docConfigurationProperties = (DocConfigurationProperties) beanRegistry.getSingleton(DocConfigurationProperties.BEAN_NAME);
        }
    }
}
