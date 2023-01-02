package com.github.fashionbrot.doc.util;

import com.github.fashionbrot.doc.vo.ParameterVo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fashionbrot
 */
public class ResponseUtil2 {


    public static List<ParameterVo> getResponse(Method method) {
        Class<?> returnClass = method.getReturnType();
        if (returnClass == null || returnClass == Void.class) {
            return null;
        }
        Type returnClassType = method.getGenericReturnType();
        List<ParameterVo> parameterList = new ArrayList<>();
        classSelector(returnClass, returnClassType, parameterList);
        return parameterList;
    }


    public static void classSelector(Class clazz, Type classType, List<ParameterVo> parameterList) {

        if (JavaUtil.isArray(clazz)) {
            parseArrayClass(clazz, classType, parameterList);
        } else if (JavaUtil.isCollection(clazz)) {
            parseListClass(clazz, classType, parameterList);
        } else if (JavaUtil.isPrimitive(clazz) || JavaUtil.isMap(clazz) || JavaUtil.isObject(clazz)) {
            parameterList.add(AnnotationUtil.parseBaseType(clazz));
        } else {
            parseClass(clazz, classType, parameterList);
        }

    }

    public static void fieldSelector(Field field , Type classType, List<ParameterVo> parameterList) {

        if (JavaUtil.isFinal(field) || AnnotationUtil.isIgnore(field)){
            return;
        }
        Class<?> fieldClass = field.getType();

//        if (JavaUtil.isArray(fieldClass)) {
//            parseFieldArray(field,classType,parameterList);
//        } else if (JavaUtil.isCollection(fieldClass)) {
//            parseFieldList(field,classType,parameterList);
//        } else if (JavaUtil.isPrimitive(fieldClass)) {
//            parameterList.add(AnnotationUtil.parseBaseType(field));
//        } else if (JavaUtil.isMap(fieldClass)) {
//            parameterList.add(AnnotationUtil.parseBaseType(field));
//        } else if (JavaUtil.isObject(fieldClass)) {
//            parseGenericField(field,classType,parameterList);
//        } else {
//            //class Field 解析
//            parseField(field, parameterList);
//        }
    }


    public static void parseClass(Class clazz,Type classType,List<ParameterVo> parameterList){

        parseSuperClass(clazz,parameterList);

        Field[] declaredFields = clazz.getDeclaredFields();
        if (ObjectUtil.isNotEmpty(declaredFields)){
            for (Field field : declaredFields) {
                fieldSelector( field , classType, parameterList);
            }
        }
    }

    public static void parseSuperClass(Class propertyClass, List<ParameterVo> parameterList) {

        Class superclass = propertyClass.getSuperclass();
        if (superclass != null && JavaUtil.isNotObject(superclass)) {

            classSelector(superclass,null, parameterList);
        }
    }


    public static void parseListClass(Class clazz,Type classType,List<ParameterVo> parameterList){
        if (classType instanceof Class){
            Type[] actualTypeArguments = TypeUtil.convertActualTypeArguments(classType);
            if (ObjectUtil.isNotEmpty(actualTypeArguments)) {
                if (actualTypeArguments[0] instanceof Class) {
                    parseClass((Class) actualTypeArguments[0], classType, parameterList);
                }
            }
        }else if ( classType instanceof ParameterizedType){
            Type[] actualTypeArguments = TypeUtil.convertActualTypeArguments(classType);
            if (ObjectUtil.isNotEmpty(actualTypeArguments)) {

                if (actualTypeArguments[0] instanceof Class){
                    Class convertClass = TypeUtil.typeConvertClass(actualTypeArguments[0]);
                    if (convertClass!=null){

                        ParameterVo parameterVo = AnnotationUtil.parseBaseType(clazz);
                        parameterVo.setCollection(1);
                        parameterVo.setDataType(convertClass.getTypeName());

                        if (JavaUtil.isPrimitive(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isObject(convertClass)){
                            parameterVo.setIsPrimitive(1);
                        }else{
                            List<ParameterVo> childList=new ArrayList<>();
                            parseClass(convertClass, actualTypeArguments[0], childList);
                            parameterVo.setChild(childList);
                        }
                        parameterList.add(parameterVo);
                    }
                }
            }
        }
    }
    public static void parseArrayClass(Class clazz, Type classType, List<ParameterVo> parameterList) {
        Class componentType = clazz.getComponentType();
        if (componentType == null || classType == null) {
            return;
        }
        if (classType instanceof Class) {
            ParameterVo parameterVo = AnnotationUtil.parseBaseType(clazz);
            parameterVo.setCollection(1);
            parameterVo.setDataType(componentType.getTypeName());
            parameterVo.setName(componentType.getTypeName());
            if (JavaUtil.isPrimitive(componentType) || JavaUtil.isMap(componentType) || JavaUtil.isObject(componentType)) {
                parameterVo.setIsPrimitive(1);
            } else {
                List<ParameterVo> childList = new ArrayList<>();
                parseClass(componentType, classType, childList);
                parameterVo.setChild(childList);
            }
            parameterList.add(parameterVo);
        }
    }


    public static void parseField(Field field, List<ParameterVo> parameterList) {

        Class<?> propertyClass = field.getType();
        List<ParameterVo> childParameterList = new ArrayList<>();

        //class parent 类解析
        parseSuperClass(propertyClass, childParameterList);

        Field[] declaredFields = propertyClass.getDeclaredFields();
        if (ObjectUtil.isNotEmpty(declaredFields)) {
            ParameterVo parameterVo = AnnotationUtil.parseBaseType(field);
            for (Field f : declaredFields) {
                parseField( f, childParameterList);
            }
            parameterVo.setChild(childParameterList);
            parameterList.add(parameterVo);
        }
    }


    public static void parseFieldArray(Field field,Type classType,List<ParameterVo> parameterList){
        ParameterVo parameterVo = AnnotationUtil.parseBaseType(field);
        List<ParameterVo> childParameterList = new ArrayList<>();

        Class componentType = TypeUtil.getFieldType(field,classType);
        if (componentType!=null){
            parameterVo.setCollection(1);
            parameterVo.setDataType(componentType.getTypeName());
            if (JavaUtil.isPrimitive(componentType) || JavaUtil.isMap(componentType) || JavaUtil.isObject(componentType)){
                parameterVo.setIsPrimitive(1);
            }else{
                parseClass(componentType,classType, childParameterList);
            }
        }

        if (ObjectUtil.isNotEmpty(childParameterList)){
            parameterVo.setChild(childParameterList);
        }
        parameterList.add(parameterVo);
    }

}
