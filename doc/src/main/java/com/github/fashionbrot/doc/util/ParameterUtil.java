package com.github.fashionbrot.doc.util;

import com.github.fashionbrot.doc.annotation.ApiModelProperty;
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

                if (JavaUtil.isNotPrimitive(field.getGenericType().getTypeName())) {

                    if (ObjectUtil.isNotEmpty(methodTypeList)){
                        Optional<MethodTypeVo> first = methodTypeList.stream().filter(m -> m.getTypeName().equals(field.getGenericType().getTypeName())).findFirst();
                        if (first.isPresent()){
                            MethodTypeVo methodType = first.get();
                            build.setDataType(methodType.getTypeClassStr());


                            if (JavaUtil.isCollection(methodType.getTypeClass()) ) {
                                build.setCollection(1);
                                if (ObjectUtil.isNotEmpty(methodType.getChild())) {
                                    //集合的泛型只有一个
                                    MethodTypeVo childMethodType = methodType.getChild().get(0);
                                    build.setChild(fieldConvertParameter(childMethodType.getTypeClass(), childMethodType.getChild(), requestType));
                                }
                            }else if (JavaUtil.isArray(methodType.getTypeClass())){
                                build.setCollection(1);
                                build.setChild(fieldConvertParameter(methodType.getTypeClass().getComponentType(), methodType.getChild(), requestType));
                            }else{
                                build.setCollection(0);
                                if (JavaUtil.isNotPrimitive(methodType.getTypeClass().getTypeName())){
                                    build.setChild(fieldConvertParameter(methodType.getTypeClass(),methodType.getChild(), requestType));
                                }
                            }
                        }
                    }else{
                        if (JavaUtil.isCollection(field.getType()) ) {
                            build.setCollection(1);
                            if (JavaUtil.isNotPrimitive(field.getType().getTypeName())){
                                Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                                if (ObjectUtil.isNotEmpty(actualTypeArguments)) {
                                    Class convertClass = MethodUtil.typeConvertClass(actualTypeArguments[0]);
                                    build.setDataType(convertClass.getTypeName());
                                    build.setChild(fieldConvertParameter(convertClass, null, requestType));
                                }
                            }
                        }else if (JavaUtil.isArray(field.getType())){
                            build.setCollection(1);
                            if (JavaUtil.isNotPrimitive(field.getType().getTypeName())){
                                Class convertClass = MethodUtil.typeConvertClass(field.getGenericType());
                                build.setDataType(convertClass.getComponentType().getTypeName());
                                build.setChild(fieldConvertParameter(convertClass.getComponentType(),null, requestType));
                            }
                        }else{
                            build.setCollection(0);
                            if (JavaUtil.isNotPrimitive(field.getType().getTypeName())){
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

                Class thisFieldClass = null;
                if (JavaUtil.isNotPrimitive(field.getGenericType().getTypeName())) {

                    if (type!=null && ParameterizedTypeEnum.isParameterizedType(type.getClass().getName())  ){

                        TypeVariable<? extends Class<?>>[] typeVariables = MethodUtil.getTypeVariable(type);
                        Type[] types = ((ParameterizedType) type).getActualTypeArguments();

                        Type childType = MethodUtil.getTypeByTypeName(types, typeVariables, fieldTypeName);
                        if (childType != null) {

                            Class fieldClass = MethodUtil.typeConvertClass(childType);
                            build.setDataType(fieldClass.getTypeName());


                            if (JavaUtil.isNotPrimitive(fieldClass.getTypeName())){
                                if (JavaUtil.isArray(fieldClass)) {
                                    fieldClass = fieldClass.getComponentType();
                                    build.setCollection(1);
                                    build.setChild(forFieldOrParam(fieldClass,childType,requestType));
                                } else if (JavaUtil.isCollection(fieldClass)) {
                                    Type[] actualTypeArguments = ((ParameterizedType) childType).getActualTypeArguments();
                                    if (ObjectUtil.isNotEmpty(actualTypeArguments)){
                                        fieldClass = MethodUtil.typeConvertClass(actualTypeArguments[0]);
                                    }
                                    build.setCollection(1);
                                    build.setChild(forFieldOrParam(fieldClass,childType,requestType));
                                } else {
                                    build.setCollection(0);
                                    build.setChild(forFieldOrParam(fieldClass,childType,requestType));
                                }
                            }
                            thisFieldClass = fieldClass;
                        }

                    }else{
                        build.setDataType(fieldTypeName);
                        if (JavaUtil.isCollection(field.getType()) ) {
                            build.setCollection(1);

                            if (JavaUtil.isNotPrimitive(field.getType().getTypeName())){

                                Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                                if (ObjectUtil.isNotEmpty(actualTypeArguments)) {
                                    Class convertClass = MethodUtil.typeConvertClass(actualTypeArguments[0]);
                                    thisFieldClass = convertClass;
                                    build.setDataType(convertClass.getTypeName());

                                    List<ParameterVo> fieldParameterList = forFieldOrParam(convertClass, null, requestType);
                                    build.setChild(fieldParameterList);
                                }
                            }
                        }else if (JavaUtil.isArray(field.getType())){
                            build.setCollection(1);
                            if (JavaUtil.isNotPrimitive(field.getType().getTypeName())){
                                Class convertClass = MethodUtil.typeConvertClass(field.getGenericType());
                                thisFieldClass = convertClass.getComponentType();
                                build.setDataType(convertClass.getComponentType().getTypeName());

                                List<ParameterVo> fieldParameterList = forFieldOrParam(convertClass.getComponentType(), null, requestType);
                                build.setChild(fieldParameterList);
                            }
                        }else{
                            build.setCollection(0);
                            if (JavaUtil.isNotPrimitive(field.getType().getTypeName())){
                                thisFieldClass = field.getType();
                                List<ParameterVo> fieldParameterList = forFieldOrParam(field.getType(), null, requestType);
                                build.setChild(fieldParameterList);
                            }
                        }

                    }
                }


                //获取 superClass的属性
                List<ParameterVo> superClassFieldList = RequestUtil.getSuperClassField(thisFieldClass, requestType);
                if (ObjectUtil.isNotEmpty(superClassFieldList)){
                    build.getChild().addAll(superClassFieldList);
                    build.setChild(build.getChild());
                }

                parameterVoList.add(build);
            }
        }
        return parameterVoList;
    }



}
