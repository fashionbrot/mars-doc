package com.github.fashionbrot.doc.util;

import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.enums.ClassTypeEnum;
import com.github.fashionbrot.doc.enums.ParameterizedTypeEnum;
import com.github.fashionbrot.doc.vo.MethodTypeVo;
import com.github.fashionbrot.doc.vo.ParameterVo;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author fashionbrot
 */
public class ParameterUtil {


    public static List<ParameterVo> fieldConvertParameter(Class clazz, List<MethodTypeVo> methodTypeList, String requestType) {

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
                        .dataType(field.getGenericType().getTypeName())
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
                                    //集合的泛型只有一个
                                    MethodTypeVo childMethodType = methodType.getChild().get(0);
                                    build.setChild(fieldConvertParameter(childMethodType.getTypeClass(), childMethodType.getChild(), requestType));
                                }
                            }else if (JavaClassValidateUtil.isArray(methodType.getTypeClass())){
                                build.setCollection(1);
                                build.setChild(fieldConvertParameter(methodType.getTypeClass().getComponentType(), methodType.getChild(), requestType));
                            }else{
                                build.setCollection(0);
                                if (!ClassTypeEnum.checkClass(methodType.getTypeClass().getTypeName())){
                                    build.setChild(fieldConvertParameter(methodType.getTypeClass(),methodType.getChild(), requestType));
                                }
                            }
                        }
                    }else{
                        if (JavaClassValidateUtil.isCollection(field.getType()) ) {
                            build.setCollection(1);
                            if (!ClassTypeEnum.checkClass(field.getType().getTypeName())){
                                Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                                if (ObjectUtil.isNotEmpty(actualTypeArguments)) {
                                    Class convertClass = MethodUtil.typeConvertClass(actualTypeArguments[0]);
                                    build.setDataType(convertClass.getTypeName());
                                    build.setChild(fieldConvertParameter(convertClass, null, requestType));
                                }
                            }
                        }else if (JavaClassValidateUtil.isArray(field.getType())){
                            build.setCollection(1);
                            if (!ClassTypeEnum.checkClass(field.getType().getTypeName())){
                                Class convertClass = MethodUtil.typeConvertClass(field.getGenericType());
                                build.setDataType(convertClass.getComponentType().getTypeName());
                                build.setChild(fieldConvertParameter(convertClass.getComponentType(),null, requestType));
                            }
                        }else{
                            build.setCollection(0);
                            if (!ClassTypeEnum.checkClass(field.getType().getTypeName())){
                                build.setChild(fieldConvertParameter(field.getType(),null, requestType));
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

    public static List<ParameterVo> forFieldOrParam(Class clazz,Type type, String requestType) {
        Field[] declaredFields = clazz.getDeclaredFields();
        List<ParameterVo> parameterVoList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(declaredFields)) {

            for (Field field : declaredFields) {
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }

                field.setAccessible(true);
                String fieldName = field.getName();
                String fieldTypeName = field.getGenericType().getTypeName();
                String fieldClassName = field.getType().getTypeName();

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
                        .dataType(fieldClassName)
                        .build();

                if (!ClassTypeEnum.checkClass(field.getGenericType().getTypeName())) {

                    if (ParameterizedTypeEnum.isParameterizedType(type.getClass().getName())  ){

                        TypeVariable<? extends Class<?>>[] typeVariables = MethodUtil.getTypeVariable(type);
                        Type[] types = ((ParameterizedType) type).getActualTypeArguments();

                        Type childType = MethodUtil.getTypeByTypeName(types, typeVariables, fieldTypeName);
                        if (childType != null) {

                            Class fieldClass = MethodUtil.typeConvertClass(childType);
                            build.setDataType(fieldClass.getTypeName());

                            if (!ClassTypeEnum.checkClass(fieldClass.getTypeName())){
                                if (JavaClassValidateUtil.isArray(fieldClass)) {
                                    build.setCollection(1);
                                    build.setChild(forFieldOrParam(fieldClass,childType,requestType));
                                } else if (JavaClassValidateUtil.isCollection(fieldClass)) {
                                    build.setCollection(1);
                                    build.setChild(forFieldOrParam(fieldClass,childType,requestType));
                                } else {
                                    build.setCollection(0);
                                    build.setChild(forFieldOrParam(fieldClass,childType,requestType));
                                }
                            }
                        }

                    }
                }
                parameterVoList.add(build);
            }
        }
        return parameterVoList;
    }



}
