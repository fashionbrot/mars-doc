package com.github.fashionbrot.doc.util;

import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.vo.ParameterVo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

/**
 * @author fashionbrot
 */
public class ApiModelPropertyUtil {


    public static ParameterVo getParameterVo(ClassType classType){
        if (classType.getObj() instanceof  Parameter){
            return parseBaseType((Parameter)classType.getObj());
        }else if (classType.getObj() instanceof Field){
            return parseBaseType((Field)classType.getObj());
        }else{
            return parseBaseType(classType.getClazz());
        }
    }

    public static ParameterVo parseBaseType(Parameter parameter) {

        String propertyName = parameter.getName();
        String example = "";
        String dataType = parameter.getType().getTypeName();
        String multiple = "";
        String description = "";
        boolean required = false;

        ApiModelProperty apiModelProperty = parameter.getDeclaredAnnotation(ApiModelProperty.class);
        if (apiModelProperty != null) {
            example = apiModelProperty.example();
            dataType = apiModelProperty.dataType();
            if (ObjectUtil.isEmpty(dataType)) {
                dataType = parameter.getType().getTypeName();
            }
            multiple = apiModelProperty.multiple();
            description = apiModelProperty.value();
            required = apiModelProperty.required();
        }

        return ParameterVo.builder()
                .name(propertyName)
                .description(description)
                .required(required)
                .dataType(dataType)
                .example(example)
                .multiple(multiple)
                .build();
    }

    public static ParameterVo parseBaseType(Field field) {

        String propertyName = field.getName();
        String example = "";
        String dataType = field.getType().getTypeName();
        String multiple = "";
        String description = "";
        boolean required = false;

        ApiModelProperty apiModelProperty = field.getDeclaredAnnotation(ApiModelProperty.class);
        if (apiModelProperty != null) {
            example = apiModelProperty.example();
            dataType = apiModelProperty.dataType();
            if (ObjectUtil.isEmpty(dataType)) {
                dataType = field.getType().getTypeName();
            }
            multiple = apiModelProperty.multiple();
            description = apiModelProperty.value();
            required = apiModelProperty.required();
        }

        return ParameterVo.builder()
                .name(propertyName)
                .description(description)
                .required(required)
                .dataType(dataType)
                .example(example)
                .multiple(multiple)
                .build();
    }

    public static ParameterVo parseBaseType(Class clazz) {

        String propertyName = clazz.getName();
        String example = "";
        String dataType = clazz.getTypeName();
        String multiple = "";
        String description = "";
        boolean required = false;

        Annotation annotation = clazz.getDeclaredAnnotation(ApiModelProperty.class);
        if (annotation != null) {
            ApiModelProperty apiModelProperty = (ApiModelProperty) annotation;

            example = apiModelProperty.example();
            dataType = apiModelProperty.dataType();
            if (ObjectUtil.isEmpty(dataType)) {
                dataType = clazz.getTypeName();
            }
            multiple = apiModelProperty.multiple();
            description = apiModelProperty.value();
            required = apiModelProperty.required();
        }

        return ParameterVo.builder()
                .name(propertyName)
                .description(description)
                .required(required)
                .dataType(dataType)
                .example(example)
                .multiple(multiple)
                .build();
    }


}
