package com.github.fashionbrot.doc.event;


import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.DocConfigurationProperties;
import com.github.fashionbrot.doc.DocParameterizedType;
import com.github.fashionbrot.doc.annotation.Api;
import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.enums.ClassTypeEnum;
import com.github.fashionbrot.doc.util.JavaClassValidateUtil;
import com.github.fashionbrot.doc.util.ObjectUtil;
import com.github.fashionbrot.doc.util.PathUtil;
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


                String methodDescription = method.getName();
                int methodPriority = 0;
                ApiOperation apiOperation = method.getDeclaredAnnotation(ApiOperation.class);
                if (apiOperation != null) {
                    methodDescription = apiOperation.value();
                    methodPriority = apiOperation.priority();
                }


                List<MethodVo> methodVoList = getRequestMapping(method);
                if (ObjectUtil.isNotEmpty(methodVoList)) {
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

    public List<MethodVo> getRequestMapping(Method method) {

        Class<?> declaringClass = method.getDeclaringClass();
        RequestMapping classRequestMapping = declaringClass.getDeclaredAnnotation(RequestMapping.class);
        String[] classPaths = null;
        if (classRequestMapping != null) {
            classPaths = classRequestMapping.value();
        }
        List<MethodVo> methodVoList = new ArrayList<>();
        if (method != null) {

            GetMapping getMapping = method.getDeclaredAnnotation(GetMapping.class);
            if (getMapping != null) {
                getMapping(getMapping.value(), "GET", methodVoList);
            }
            PostMapping postMapping = method.getDeclaredAnnotation(PostMapping.class);
            if (postMapping != null) {
                getMapping(postMapping.value(), "POST", methodVoList);
            }
            PutMapping putMapping = method.getDeclaredAnnotation(PutMapping.class);
            if (putMapping != null) {
                getMapping(putMapping.value(), "PUT", methodVoList);
            }
            DeleteMapping deleteMapping = method.getDeclaredAnnotation(DeleteMapping.class);
            if (deleteMapping != null) {
                getMapping(deleteMapping.value(), "DELETE", methodVoList);
            }
            RequestMapping mapping = method.getDeclaredAnnotation(RequestMapping.class);
            if (mapping != null) {
                RequestMethod[] requestMethods = mapping.method();
                if (ObjectUtil.isNotEmpty(requestMethods)) {
                    for (RequestMethod request : requestMethods) {
                        getMapping(mapping.value(), request.name(), methodVoList);
                    }
                } else {
                    for (RequestMethod request : RequestMethod.values()) {
                        getMapping(mapping.value(), request.name(), methodVoList);
                    }
                }
            }
        }
        if (ObjectUtil.isNotEmpty(classPaths)) {
            List<MethodVo> list = new ArrayList<>();
            for (int i = 0; i < classPaths.length; i++) {
                String classPath = classPaths[i];
                for (MethodVo mv : methodVoList) {
                    list.add(MethodVo.builder()
                            .path(PathUtil.formatPath(classPath, mv.getPath()))
                            .method(mv.getMethod())
                            .build());
                }
            }
            return list;
        }

        return methodVoList;
    }

    private void getMapping(String[] values, String method, List<MethodVo> methodVoList) {
        if (ObjectUtil.isNotEmpty(values)) {
            for (String path : values) {
                methodVoList.add(MethodVo.builder()
                        .method(method)
                        .path(path)
                        .build());
            }
        }
    }






    private List<ParameterVo> getResponse(Method method) {

        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType!=null){
            System.out.println(genericReturnType.getTypeName());
        }




        List<ParameterVo> list = new ArrayList<>();
        Class<?> returnType = method.getReturnType();
        if (returnType != Void.class) {

            MethodTypeVo methodTypeRoot = getMethodTypeVo(method);

//            String methodId = getMethodId(method);
//            Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();


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
        String methodId = getMethodId(method);

        Type genericReturnType = method.getGenericReturnType();
        TypeVariable<? extends Class<?>>[] typeParameters = method.getReturnType().getTypeParameters();

        List<MethodTypeVo> methodTypeVoList= new ArrayList<>();

        if (ObjectUtil.isNotEmpty(typeParameters)){
            Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
            for (int i = 0; i < typeParameters.length; i++) {
                Type  classType = actualTypeArguments[i];
                Type  genType = typeParameters[i];

                Class typeClass = convertClass(classType);

                MethodTypeVo build = MethodTypeVo.builder()
                        .methodId(methodId)
                        .typeClass(typeClass)
                        .typeClassStr(classType.getTypeName())
                        .typeName(genType.getTypeName())
                        .build();


                if (!ClassTypeEnum.checkClass(classType.getTypeName())){

                    if (typeClass.isArray()){
                        System.out.println(typeClass.getComponentType());
                        //TODO 需要继续
                        DocParameterizedType parameterizedType = new DocParameterizedType(List.class, new Class[]{typeClass.getComponentType()},null);
                        List<MethodTypeVo> childList = getMethodTypeList(parameterizedType, genType, methodId);
//                        build.setTypeClass(typeClass.getComponentType());
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
                .typeClass(convertClass(genericReturnType))
                .typeName(genericReturnType.getTypeName())
                .typeClassStr("root")
                .child(methodTypeVoList)
                .build();
        return root;
    }

    public boolean isParameterizedType(String typeClassName){
        if ("sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl".equals(typeClassName)
           || "com.github.fashionbrot.doc.DocParameterizedType".equals(typeClassName)){
            return true;
        }
        return false;
    }

    public List<MethodTypeVo> getMethodTypeList(Type classType,Type  genType,String methodId){
        List<MethodTypeVo> list=new ArrayList<>();


        if (!isParameterizedType(classType.getClass().getName())  ){
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

                Class typeClass = convertClass(type);

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

    private Class convertClass(Type type) {
        Class typeClass = null;
        if(type instanceof  Class){
            typeClass = (Class) type;
        }else if (type instanceof ParameterizedType){
            typeClass = (Class) ((ParameterizedType) type).getRawType();
        }
        return typeClass;
    }


    public String getMethodId(Method method){
        Class<?> declaringClass = method.getDeclaringClass();
        return declaringClass.getName()+"#"+method.getName();
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
                    String desc = "";
                    boolean required = false;
                    ApiModelProperty apiModelProperty = parameter.getDeclaredAnnotation(ApiModelProperty.class);
                    if (apiModelProperty != null) {
                        desc = apiModelProperty.value();
                        required = apiModelProperty.required();
                        if (apiModelProperty.hidden()) {
                            continue;
                        }
                    }

                    parameterVoList.add(ParameterVo.builder()
                            .name(parameterName)
                            .in("query")
                            .description(desc)
                            .required(required)
                            .dataType(type.getSimpleName())
                            .build());

                } else {

                    RequestBody requestBody = parameter.getDeclaredAnnotation(RequestBody.class);
                    String in = requestBody != null ? "body" : "query";
                    ParameterVo req = null;
                    if (requestBody != null) {
                        String description = parameterName;
                        ApiModel apiModel = classType.getDeclaredAnnotation(ApiModel.class);
                        if (apiModel != null) {
                            description = apiModel.value();
                        }

                        req = ParameterVo.builder()
                                .name(parameterName)
                                .in("body")
                                .required(true)
                                .description(description)
                                .build();
                    }
                    List<ParameterVo> parameterVos = fieldConvertParameter(classType,null, in);
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

    public List<ParameterVo> fieldConvertParameter(Class clazz,List<MethodTypeVo> methodTypeList, String in) {

        Field[] declaredFields = clazz.getDeclaredFields();
        if (ObjectUtil.isNotEmpty(declaredFields)) {
            List<ParameterVo> parameterVoList = new ArrayList<>();
            for (Field field : declaredFields) {
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }


                System.out.println(field.getGenericType());
                field.setAccessible(true);
                String fieldName = field.getName();
                Class<?> valueType = field.getType();
                String desc = "";
                boolean required = false;
                ApiModelProperty apiModelProperty = field.getDeclaredAnnotation(ApiModelProperty.class);
                if (apiModelProperty != null) {
                    desc = apiModelProperty.value();
                    required = apiModelProperty.required();
                    if (apiModelProperty.hidden()) {
                        continue;
                    }
                }
                ParameterVo build = ParameterVo.builder()
                        .name(fieldName)
                        .in(in)
                        .description(desc)
                        .required(required)
                        .dataType(valueType.getSimpleName())
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
                                    build.setChild(fieldConvertParameter(childMethodType.getTypeClass(), childMethodType.getChild(), in));
                                }
                            }else if (JavaClassValidateUtil.isArray(methodType.getTypeClass())){
                                build.setCollection(1);

                                build.setChild(fieldConvertParameter(methodType.getTypeClass().getComponentType(), methodType.getChild(), in));
                            }else{
                                build.setCollection(0);
                                if (ObjectUtil.isNotEmpty(methodType.getChild())){
                                    build.setChild(fieldConvertParameter(methodType.getTypeClass(),methodType.getChild(), in));
                                }
                            }
                            System.out.println(methodType.getTypeName());

                        }
                    }
//                    else{
//                        build.setChild(fieldConvertParameter(valueType,null, in));
//                    }

                }
                parameterVoList.add(build);
            }
            return parameterVoList;
        }
        return null;
    }

    /**
     * 判断返回值类型是否是集合或者数组类型
     * @param returnType 类型
     * @return 是否是集合或者数组类型
     */
    public boolean returnsMany(Class<?> returnType) {
        //判断返回类型是否是集合类型
        boolean isCollection = Collection.class.isAssignableFrom(returnType);

        boolean isIterable = Iterable.class.isAssignableFrom(returnType);
        //判断返回类型是否是数组类型
        boolean isArray = returnType.isArray();
        return isCollection || isArray || isIterable;
    }


    public Integer getTypeIndex(TypeVariable<? extends Class<? extends Class>>[] typeParameters,Field field ){
        Type genericType = field.getGenericType();
        if (ObjectUtil.isNotEmpty(typeParameters)){
            for (int i = 0; i < typeParameters.length; i++) {
                if (typeParameters[i].getTypeName().equals(genericType.getTypeName())){
                    return i;
                }
            }
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
