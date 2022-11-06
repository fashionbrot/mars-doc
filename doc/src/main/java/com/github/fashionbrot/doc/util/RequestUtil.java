package com.github.fashionbrot.doc.util;

import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.enums.ClassTypeEnum;
import com.github.fashionbrot.doc.enums.RequestTypeEnum;
import com.github.fashionbrot.doc.vo.ParameterVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author fashionbrot
 */
public class RequestUtil {

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

        if (ObjectUtil.isNotEmpty(parameters)) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];

                Class<?> parameterClass = parameter.getType();
                String parameterName = parameter.getName();

                RequestBody requestBody = parameter.getDeclaredAnnotation(RequestBody.class);
                String requestType = requestBody != null ? RequestTypeEnum.BODY.name() : RequestTypeEnum.QUERY.name();

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
                    if (requestBody != null) {
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
        return parameterVoList;
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
