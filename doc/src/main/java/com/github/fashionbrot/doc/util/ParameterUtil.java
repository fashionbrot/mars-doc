package com.github.fashionbrot.doc.util;

import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.enums.ParameterizedTypeEnum;
import com.github.fashionbrot.doc.vo.ParameterVo;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fashionbrot
 */
public class ParameterUtil {





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

                        TypeVariable<? extends Class<?>>[] typeVariables = TypeUtil.getTypeVariable(type);
                        Type[] types = ((ParameterizedType) type).getActualTypeArguments();

                        Type childType = TypeUtil.getTypeByTypeName(types, typeVariables, fieldTypeName);
                        if (childType != null) {

                            Class fieldClass = TypeUtil.typeConvertClass(childType);
                            build.setDataType(fieldClass.getTypeName());


                            if (JavaUtil.isNotPrimitive(fieldClass.getTypeName())){
                                if (JavaUtil.isArray(fieldClass)) {
                                    fieldClass = fieldClass.getComponentType();
                                    build.setCollection(1);
                                    build.setChild(forFieldOrParam(fieldClass,childType,requestType));
                                } else if (JavaUtil.isCollection(fieldClass)) {
                                    Type[] actualTypeArguments = ((ParameterizedType) childType).getActualTypeArguments();
                                    if (ObjectUtil.isNotEmpty(actualTypeArguments)){
                                        fieldClass = TypeUtil.typeConvertClass(actualTypeArguments[0]);
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
                                    Class convertClass = TypeUtil.typeConvertClass(actualTypeArguments[0]);
                                    thisFieldClass = convertClass;
                                    build.setDataType(convertClass.getTypeName());

                                    List<ParameterVo> fieldParameterList = forFieldOrParam(convertClass, null, requestType);
                                    build.setChild(fieldParameterList);
                                }
                            }
                        }else if (JavaUtil.isArray(field.getType())){
                            build.setCollection(1);
                            if (JavaUtil.isNotPrimitive(field.getType().getTypeName())){
                                Class convertClass = TypeUtil.typeConvertClass(field.getGenericType());
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
                List<ParameterVo> superClassFieldList = RequestUtilOld.getSuperClassField(thisFieldClass, requestType);
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
