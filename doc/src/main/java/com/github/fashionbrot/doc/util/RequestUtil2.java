package com.github.fashionbrot.doc.util;

import com.github.fashionbrot.doc.annotation.ApiIgnore;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.vo.ParameterVo;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fashionbrot
 */
public class RequestUtil2 {

    /**
     * 获取方法参数
     *
     * @param method method
     * @return List
     */
    public static List<ParameterVo> getRequest3(Method method) {
        List<ParameterVo> parameterList = new ArrayList<>();
        if (method == null) {
            return parameterList;
        }

        Parameter[] parameterArray = method.getParameters();
        if (ObjectUtil.isNotEmpty(parameterArray)) {
            for (int i = 0; i < parameterArray.length; i++) {
                Parameter parameter = parameterArray[i];

                parseParameter(parameter, parameterList);
            }
        }
        return parameterList;
    }


    public static void parseParameter(Parameter parameter, List<ParameterVo> parameterList) {

        ApiIgnore apiIgnore = parameter.getDeclaredAnnotation(ApiIgnore.class);
        if (apiIgnore != null) {
            return;
        }
        ApiModelProperty apiModelProperty = parameter.getDeclaredAnnotation(ApiModelProperty.class);
        if (apiModelProperty != null && apiModelProperty.hidden()) {
            return;
        }
        Class<?> propertyClass = parameter.getType();
        String propertyName = parameter.getName();

        if (JavaUtil.isArray(propertyClass)) {
            //数组类型
        } else if (JavaUtil.isCollection(propertyClass)) {
            //集合类型

        } else if (JavaUtil.isBaseType(propertyClass)) {
            //基本类型
            parameterList.add(AnnotationUtil.parseBaseType(parameter));
        } else if (JavaUtil.isObject(propertyClass)) {
            //java.lang.Object类型
            parameterList.add(AnnotationUtil.parseBaseType(parameter));
        } else {
            //解析Class
            parseParameterClass(ClassTypeUtil.getClassType(parameter), parameterList);
        }
    }


    public static void parseParameterClass(ClassType classType, List<ParameterVo> parameterList) {
        //TODO 先注释
//        ParameterVo parameterVo = AnnotationUtil.getParameterVo(classType);
//        List<ParameterVo> childParameterList = new ArrayList<>();
//
//        //class parent 类解析
//        parseSuperClass(classType, childParameterList);
//
//
//        Field[] declaredFields = classType.getClazz().getDeclaredFields();
//        if (ObjectUtil.isNotEmpty(declaredFields)) {
//            for (Field field : declaredFields) {
//                checkParseField(classType, field, childParameterList);
//            }
//            parameterVo.setChild(childParameterList);
//            parameterList.add(parameterVo);
//        }
    }



    public static void parseSuperClass(ClassType classType, List<ParameterVo> parameterList) {

        Class superclass = classType.getClazz().getSuperclass();
        if (superclass != null && JavaUtil.isNotObject(superclass)) {

            parseSuperClassField(superclass,parameterList);

        }
    }

    public static void parseSuperClassField(Class clazz ,List<ParameterVo> parameterList){
        Field[] declaredFields = clazz.getDeclaredFields();
        if (ObjectUtil.isNotEmpty(declaredFields)) {
            for (Field field : declaredFields) {
//                parseField(classType, field, parameterList);
            }
        }
    }


    public static void checkParseField(ClassType classType, Field field, List<ParameterVo> parameterList) {
        if (Modifier.isFinal(field.getModifiers())) {
            return;
        }
        field.setAccessible(true);
        ApiIgnore ignore = field.getDeclaredAnnotation(ApiIgnore.class);
        if (ignore != null) {
            return;
        }
        ApiModelProperty apiModelProperty = field.getDeclaredAnnotation(ApiModelProperty.class);
        if (apiModelProperty != null && apiModelProperty.hidden()) {
            return;
        }

        parseFieldType(classType, field, parameterList);

    }




    public static void parseFieldType(ClassType classType, Field field, List<ParameterVo> parameterList) {

//        ApiIgnore apiIgnore = field.getDeclaredAnnotation(ApiIgnore.class);
//        if (apiIgnore != null) {
//            return;
//        }
//        ApiModelProperty apiModelProperty = field.getDeclaredAnnotation(ApiModelProperty.class);
//        if (apiModelProperty != null && apiModelProperty.hidden()) {
//            return;
//        }

        Class<?> propertyClass = field.getType();
        String propertyName = field.getName();

        if (JavaUtil.isArray(propertyClass)) {

        } else if (JavaUtil.isCollection(propertyClass)) {

        } else if (JavaUtil.isBaseType(propertyClass)) {
            parameterList.add(AnnotationUtil.parseBaseType(field));
        } else if (JavaUtil.isObject(propertyClass)) {

            Type filedClass = TypeUtil.getTypeByTypeName(classType.getActualTypeArguments(), classType.getTypeVariables(), field.getGenericType().getTypeName());
            if (filedClass!=null){
//                parseTypeField(filedClass,parameterList);
            }
        } else {

            /**
             * class Field 解析
             */
            parseClassField(classType,ClassTypeUtil.getClassType(field), parameterList);
        }
    }

    public static void  parseClassField(ClassType classType,ClassType fieldType, List<ParameterVo> parameterVoList){
        System.out.println(11);
    }


    public static void checkParseClass(Class clazz,List<ParameterVo> parameterVoList){

    }

}
