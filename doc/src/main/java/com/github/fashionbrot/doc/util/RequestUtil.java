package com.github.fashionbrot.doc.util;

import com.github.fashionbrot.doc.annotation.ApiIgnore;
import com.github.fashionbrot.doc.annotation.ApiImplicitParam;
import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.enums.ClassTypeEnum;
import com.github.fashionbrot.doc.enums.ParamTypeEnum;
import com.github.fashionbrot.doc.vo.ParameterVo;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author fashionbrot
 */
public class RequestUtil {

    public static final String REQUEST_BODY="org.springframework.web.bind.annotation.RequestBody";
    public static final String REQUEST_BODY_REQUIRED = "required";

    public static boolean getRequestBodyRequired(Annotation annotation, Method method){
        Object invoke = null;
        try {
            invoke = method.invoke(annotation, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (invoke instanceof Boolean){
            return (Boolean) invoke;
        }
        return true;
    }

//    public static List<ParameterVo> getRequest(Method method) {
//        Parameter[] parameters = method.getParameters();
//
//
//        List<ParameterVo> parameterVoList = new ArrayList<>();
//
//        if (ObjectUtil.isNotEmpty(parameters)) {
//            for (int i = 0; i < parameters.length; i++) {
//                Parameter parameter = parameters[i];
//
//                Class<?> parameterClass = parameter.getType();
//                String parameterName = parameter.getName();
//
//                RequestBody requestBody = parameter.getDeclaredAnnotation(RequestBody.class);
//                String requestType = requestBody != null ? RequestTypeEnum.BODY.name() : RequestTypeEnum.QUERY.name();
//
//                if (ClassTypeEnum.checkClass(parameterClass.getName())) {
//
//
//                    Class<?> type = parameter.getType();
//                    String parameterDescription = "";
//                    boolean required = false;
//                    ApiModelProperty apiModelProperty = parameter.getDeclaredAnnotation(ApiModelProperty.class);
//                    if (apiModelProperty != null) {
//                        parameterDescription = apiModelProperty.value();
//                        required = apiModelProperty.required();
//                        if (apiModelProperty.hidden()) {
//                            continue;
//                        }
//                    }
//
//                    parameterVoList.add(ParameterVo.builder()
//                            .name(parameterName)
//                            .requestType(requestType)
//                            .description(parameterDescription)
//                            .required(required)
//                            .dataType(type.getSimpleName())
//                            .build());
//
//                } else {
//
//                    ParameterVo req = null;
//                    if (requestBody != null) {
//                        String description = parameterName;
//                        ApiModel apiModel = parameterClass.getDeclaredAnnotation(ApiModel.class);
//                        if (apiModel != null) {
//                            description = apiModel.value();
//                        }
//
//                        req = ParameterVo.builder()
//                                .name(parameterName)
//                                .requestType(requestType)
//                                .required(true)
//                                .description(description)
//                                .build();
//                    }
//                    List<ParameterVo> parameterVos = ParameterUtil.fieldConvertParameter(parameterClass, null, requestType);
//                    List<ParameterVo> superField = getSuperClassField(parameterClass, requestType);
//                    if (ObjectUtil.isNotEmpty(superField)){
//                        parameterVos.addAll(superField);
//                    }
//                    if (req != null) {
//                        req.setChild(parameterVos);
//                        parameterVoList.add(req);
//                    } else {
//                        if (ObjectUtil.isNotEmpty(parameterVos)) {
//                            parameterVoList.addAll(parameterVos);
//                        }
//                    }
//
//                }
//
//            }
//        }
//        return parameterVoList;
//    }



    public static List<ParameterVo> getRequest(Method method) {
        Parameter[] parameters = method.getParameters();


        List<ParameterVo> parameterVoList = new ArrayList<>();

        List<ParameterVo> apiImplicitParamList = parseApiImplicitParam(method);

        if (ObjectUtil.isNotEmpty(parameters)) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];

                ApiIgnore apiIgnore = parameter.getDeclaredAnnotation(ApiIgnore.class);
                if (apiIgnore!=null){
                    continue;
                }

                Class<?> parameterClass = parameter.getType();
                String parameterName = parameter.getName();

                boolean requestBodyRequired = getRequestBodyRequired(parameter);

                String requestType = requestBodyRequired ? ParamTypeEnum.BODY.name() : ParamTypeEnum.QUERY.name();

                if (ClassTypeEnum.checkClass(parameterClass.getName())) {


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
                            .requestType(requestType)
                            .description(parameterDescription)
                            .required(required)
                            .dataType(type.getSimpleName())
                            .build());

                } else {

                    ParameterVo req = null;
                    if (requestBodyRequired) {
                        String description = parameterName;
                        ApiModel apiModel = parameterClass.getDeclaredAnnotation(ApiModel.class);
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
                    List<ParameterVo> parameterVos = ParameterUtil.forFieldOrParam(parameterClass, null, requestType);
                    List<ParameterVo> superField = getSuperClassField(parameterClass, requestType);
                    if (ObjectUtil.isNotEmpty(superField)){
                        parameterVos.addAll(superField);
                    }
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
        if (ObjectUtil.isNotEmpty(parameterVoList) && ObjectUtil.isNotEmpty(apiImplicitParamList)){
            parameterVoList.addAll(apiImplicitParamList);
        }
        return parameterVoList;
    }

    private static boolean getRequestBodyRequired(Parameter parameter) {

        Optional<Annotation> requestBodyAnnotation = Arrays.stream(parameter.getDeclaredAnnotations()).filter(m -> REQUEST_BODY.equals(m.annotationType().getTypeName())).findFirst();
        if (requestBodyAnnotation.isPresent()){
            Annotation annotation = requestBodyAnnotation.get();
            Optional<Method> requestBodyRequiredMethod = Arrays.stream(annotation.annotationType().getDeclaredMethods()).filter(m -> REQUEST_BODY_REQUIRED.equals(m.getName())).findFirst();
            if (requestBodyRequiredMethod.isPresent()){
                return  getRequestBodyRequired(annotation, requestBodyRequiredMethod.get());
            }
        }
        return false;
    }

    public static List<ParameterVo> parseApiImplicitParam(Method method){
        ApiImplicitParam[] apiImplicitParams = method.getDeclaredAnnotationsByType(ApiImplicitParam.class);
        if (ObjectUtil.isNotEmpty(apiImplicitParams)){
            return Arrays.stream(apiImplicitParams)
                    .filter(m -> checkParamType(m.paramType()))
                    .map(m-> buildParameterVo(m))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public static boolean checkParamType(String paramType){
        if (ParamTypeEnum.BODY.name().equalsIgnoreCase(paramType) || ParamTypeEnum.QUERY.name().equalsIgnoreCase(paramType)){
            return false;
        }
        return true;
    }

    public static ParameterVo buildParameterVo(ApiImplicitParam param){
        return ParameterVo.builder()
                .name(param.name())
                .description(param.value())
                .requestType(param.paramType())
                .required(param.required())
                .dataType(param.dataType())
                .example(param.defaultValue())
                .build();
    }

    public static List<ParameterVo>  getSuperClassField(Class parameterClass,  String requestType) {
        if (parameterClass != null) {
            Class superClass = parameterClass.getSuperclass();
            if (superClass != null) {

                if (!JavaClassValidateUtil.isObject(superClass)){
                    TypeVariable<?>[] typeParameters = superClass.getTypeParameters();

                    Type[] actualTypeArguments = null;
                    Type genericSuperclass = parameterClass.getGenericSuperclass();
                    if (genericSuperclass instanceof ParameterizedType) {
                        actualTypeArguments = ((ParameterizedType) parameterClass.getGenericSuperclass()).getActualTypeArguments();
                    }

                    List<ParameterVo> parameterVoList = resolveSuperField(superClass, typeParameters, actualTypeArguments, requestType);
                    return parameterVoList;
                }
            }
        }
        return null;
    }


    public static List<ParameterVo> resolveSuperField(Class superClass, TypeVariable<?>[] typeVariables, Type[] types, String requestType) {

        List<ParameterVo> parameterList = new ArrayList<>();
        Field[] declaredFields = superClass.getDeclaredFields();
        if (ObjectUtil.isNotEmpty(declaredFields)) {
            for (Field field : declaredFields) {
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                String name = field.getName();
                String className = field.getGenericType().getTypeName();
                String typeClassName = field.getType().getTypeName();

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
                        .name(name)
                        .requestType(requestType)
                        .required(required)
                        .dataType(typeClassName)
                        .description(fieldDescription)
                        .build();

                if (!ClassTypeEnum.checkClass(className)) {
                    Type type = getTypeByTypeName(types, typeVariables, className);
                    if (type != null) {
                        Class fieldClass = MethodUtil.typeConvertClass(type);
                        build.setDataType(fieldClass.getTypeName());
                    }
                }

                parameterList.add(build);
            }
            List<ParameterVo> superField = getSuperClassField(superClass, requestType);
            if (ObjectUtil.isNotEmpty(superField)) {
                parameterList.addAll(superField);
            }
            return parameterList;
        }

        return parameterList;
    }

    public static Integer getTypeVariableIndex(TypeVariable<?>[] typeVariables, String fieldTypeName) {
        if (ObjectUtil.isNotEmpty(typeVariables)) {
            for (int i = 0; i < typeVariables.length; i++) {
                TypeVariable<?> typeVariable = typeVariables[i];
                if (typeVariable.getTypeName().equals(fieldTypeName)) {
                    return i;
                }
            }
        }
        return null;
    }

    public static Type getTypeByTypeName(Type[] types, TypeVariable<?>[] typeVariables, String fieldTypeName) {
        if (ObjectUtil.isNotEmpty(types)) {
            Integer typeVariableIndex = getTypeVariableIndex(typeVariables, fieldTypeName);
            if (typeVariableIndex != null) {
                Type type = types[typeVariableIndex];
                return type;
            }
        }
        return null;
    }


}
