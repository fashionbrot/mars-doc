package com.github.fashionbrot.doc.util;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

/**
 * @author fashionbrot
 */
public class ClassTypeUtil {

    public static ClassType getClassType(Parameter parameter){
        return ClassType.builder()
                .obj(parameter)
                .clazz(parameter.getType())
                .attributeName(parameter.getName())
                .typeVariable(parameter.getType().getTypeName())
                .actualTypeArguments(TypeUtil.getActualTypeArguments(parameter))
                .typeVariables(TypeUtil.getTypeVariable(parameter))
                .build();
    }

    public static ClassType getClassType(Field field){
        return ClassType.builder()
                .obj(field)
                .clazz(field.getType())
                .attributeName(field.getName())
                .typeVariable(field.getType().getTypeName())
                .actualTypeArguments(TypeUtil.getActualTypeArguments(field))
                .typeVariables(TypeUtil.getTypeVariable(field))
                .build();
    }

}
