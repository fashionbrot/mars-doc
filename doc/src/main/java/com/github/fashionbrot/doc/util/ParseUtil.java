package com.github.fashionbrot.doc.util;

import com.github.fashionbrot.doc.annotation.ApiIgnore;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.vo.ParameterVo;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fashionbrot
 */
@Slf4j
public class ParseUtil {


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
//                parameter.getParameterizedType()
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
        if (JavaUtil.isMvcIgnoreParams(parameter.getType().getTypeName())){
            return;
        }
        Class<?> propertyClass = parameter.getType();
        String propertyName = parameter.getName();

        if (JavaUtil.isArray(propertyClass)) {
            //数组类型
            parseParameterArray(parameter,parameterList);
        } else if (JavaUtil.isCollection(propertyClass)) {
            //集合类型
            parseParameterList(parameter,parameterList);
        } else if (JavaUtil.isPrimitive(propertyClass)) {
            //基本类型
            parameterList.add(AnnotationUtil.parseBaseType(parameter));
        } else if (JavaUtil.isObject(propertyClass) || JavaUtil.isMap(propertyClass)) {
            //java.lang.Object类型
            parameterList.add(AnnotationUtil.parseBaseType(parameter));
        } else {
            //解析Class
            parseClass(parameter, parameterList);
        }
    }





    public static void parsePropertyField(Field field, List<ParameterVo> parameterList) {

        ApiIgnore apiIgnore = field.getDeclaredAnnotation(ApiIgnore.class);
        if (apiIgnore != null) {
            return;
        }
        ApiModelProperty apiModelProperty = field.getDeclaredAnnotation(ApiModelProperty.class);
        if (apiModelProperty != null && apiModelProperty.hidden()) {
            return;
        }
        Class<?> propertyClass = field.getType();
        String propertyName = field.getName();

        if (JavaUtil.isArray(propertyClass)) {
            parseFieldArray(field,parameterList);
        } else if (JavaUtil.isCollection(propertyClass)) {
            parseFieldList(field,parameterList);
        } else if (JavaUtil.isPrimitive(propertyClass)) {
            parameterList.add(AnnotationUtil.parseBaseType(field));
        } else if (JavaUtil.isObject(propertyClass) || JavaUtil.isMap(propertyClass)) {
            parameterList.add(AnnotationUtil.parseBaseType(field));
        } else {
            //class Field 解析
            parseClassField(field, parameterList);
        }
    }

    public static void parseClass(Class clazz ,List<ParameterVo> parameterList){

        if (JavaUtil.isArray(clazz)) {
//            System.out.println("class array");
            parseClassArray(clazz,parameterList);
        } else if (JavaUtil.isCollection(clazz)) {
//            System.out.println("class array");
            parseClassList(clazz,parameterList);
        } else if (JavaUtil.isPrimitive(clazz)) {
            parameterList.add(AnnotationUtil.parseBaseType(clazz));
        } else if (JavaUtil.isObject(clazz) || JavaUtil.isMap(clazz)) {
            parameterList.add(AnnotationUtil.parseBaseType(clazz));
        } else {
            /**
             * class Field 解析
             */
            parseClassField(true,clazz, parameterList);
        }
    }





    public static void parseSuperClass(Class propertyClass, List<ParameterVo> parameterList) {

        Class superclass = propertyClass.getSuperclass();
        if (superclass != null && JavaUtil.isNotObject(superclass)) {

            /**
             * class Field 解析
             */
            parseClassField(true, superclass, parameterList);
        }
    }


    public static void parseSuperClass(Parameter parameter, List<ParameterVo> parameterList) {

        Class superclass = parameter.getType().getSuperclass();
        if (superclass != null && JavaUtil.isNotObject(superclass)) {

            /**
             * class Field 解析
             */
            parseClassField(true, superclass, parameterList);
        }
    }


    public static void parseClass(Parameter parameter, List<ParameterVo> parameterList) {

        ParameterVo parameterVo = AnnotationUtil.parseBaseType(parameter);
        List<ParameterVo> childParameterList = new ArrayList<>();

        Class<?> propertyClass = parameter.getType();
        /**
         * class parent 类解析
         */
        parseSuperClass(parameter, childParameterList);


        Field[] declaredFields = propertyClass.getDeclaredFields();
        if (ObjectUtil.isNotEmpty(declaredFields)) {
            for (Field field : declaredFields) {
                parseField( field, childParameterList);
            }
            parameterVo.setChild(childParameterList);
            parameterList.add(parameterVo);
        }
    }

    public static void parseClassField(Field field, List<ParameterVo> parameterList) {

        Class<?> propertyClass = field.getType();
        Field[] declaredFields = propertyClass.getDeclaredFields();
        List<ParameterVo> childParameterList = new ArrayList<>();

        /**
         * class parent 类解析
         */
        parseSuperClass(propertyClass, childParameterList);

        if (ObjectUtil.isNotEmpty(declaredFields)) {
            ParameterVo parameterVo = AnnotationUtil.parseBaseType(field);

            for (Field f : declaredFields) {

                parseField( f, childParameterList);
            }
            parameterVo.setChild(childParameterList);
            parameterList.add(parameterVo);
        }
    }


    /**
     * 解析 Class Field
     *
     * @param clazz         clazz
     * @param parameterList 返回List
     */
    public static void parseClassField(boolean isParent,Class clazz, List<ParameterVo> parameterList) {

        Field[] declaredFields = clazz.getDeclaredFields();
        if (ObjectUtil.isNotEmpty(declaredFields)) {
            if (isParent){

                /**
                 * class parent 类解析
                 */
                parseSuperClass(clazz, parameterList);

                for (Field field : declaredFields) {
                    parseField( field, parameterList);
                }
            }else{
                ParameterVo parameterVo = AnnotationUtil.parseBaseType(clazz);
                List<ParameterVo> childParameterList = new ArrayList<>();

                /**
                 * class parent 类解析
                 */
                parseSuperClass( clazz, childParameterList);

                for (Field field : declaredFields) {
                    parseField( field, childParameterList);
                }
                parameterVo.setChild(childParameterList);
                parameterList.add(parameterVo);
            }
        }
    }


    public static void parseField( Field field, List<ParameterVo> parameterList) {
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
        parsePropertyField( field, parameterList);
    }



    public static void parseParameterList(Parameter parameter,List<ParameterVo> parameterList ){
        ParameterVo parameterVo = AnnotationUtil.parseBaseType(parameter);

        List<ParameterVo> childParameterList = new ArrayList<>();
        Type[] actualTypeArguments = TypeUtil.getActualTypeArguments(parameter);
        if (ObjectUtil.isNotEmpty(actualTypeArguments) ){

            parameterVo.setCollection(1);
            if (actualTypeArguments[0] instanceof Class){
                parameterVo.setDataType(((Class) actualTypeArguments[0]).getTypeName());
                if (JavaUtil.isPrimitive((Class) actualTypeArguments[0])) {
                    parameterVo.setIsPrimitive(1);
                }
                parseClass((Class) actualTypeArguments[0],childParameterList);
            }
        }
        if (ObjectUtil.isNotEmpty(childParameterList)){
            parameterVo.setChild(childParameterList);
        }
        parameterList.add(parameterVo);
    }

    public static void parseParameterArray(Parameter parameter,List<ParameterVo> parameterList){
        ParameterVo parameterVo = AnnotationUtil.parseBaseType(parameter);

        List<ParameterVo> childParameterList = new ArrayList<>();
        Class convertClass =parameter.getType().getComponentType();
        if (convertClass!=null){
            if (JavaUtil.isPrimitive(convertClass)) {
                parameterVo.setIsPrimitive(1);
            }
            parseClass(convertClass,childParameterList);
        }
        if (ObjectUtil.isNotEmpty(childParameterList)){
            parameterVo.setChild(childParameterList);
            parameterVo.setCollection(1);
            parameterVo.setDataType(convertClass.getTypeName());
        }
        parameterList.add(parameterVo);
    }

    public static void parseFieldList(Field field,List<ParameterVo> parameterList){
        ParameterVo parameterVo = AnnotationUtil.parseBaseType(field);

        List<ParameterVo> childParameterList = new ArrayList<>();
        Type[] actualTypeArguments = TypeUtil.getActualTypeArguments(field);
        if (ObjectUtil.isNotEmpty(actualTypeArguments)){
            parameterVo.setCollection(1);

            if (actualTypeArguments[0] instanceof Class) {

                parameterVo.setDataType(((Class) actualTypeArguments[0]).getTypeName());

                if (JavaUtil.isPrimitive((Class) actualTypeArguments[0])) {
                    parameterVo.setIsPrimitive(1);
                }
                parseClass((Class) actualTypeArguments[0], childParameterList);
            }
        }
        if (ObjectUtil.isNotEmpty(childParameterList)){
            parameterVo.setChild(childParameterList);
        }
        parameterList.add(parameterVo);
    }

    public static void parseFieldArray(Field field,List<ParameterVo> parameterList){
        ParameterVo parameterVo = AnnotationUtil.parseBaseType(field);

        List<ParameterVo> childParameterList = new ArrayList<>();
        Class convertClass =field.getType().getComponentType();
        if (convertClass!=null){
            if (JavaUtil.isPrimitive(convertClass)) {
                parameterVo.setIsPrimitive(1);
            }
            parseClass(convertClass, childParameterList);
        }

        if (ObjectUtil.isNotEmpty(childParameterList)){
            parameterVo.setChild(childParameterList);
            parameterVo.setCollection(1);
            parameterVo.setDataType(convertClass.getTypeName());
        }
        parameterList.add(parameterVo);
    }


    public static void parseClassList(Class clazz,List<ParameterVo> parameterList){
        ParameterVo parameterVo = AnnotationUtil.parseBaseType(clazz);

        List<ParameterVo> childParameterList = new ArrayList<>();
        Type[] actualTypeArguments = clazz.getGenericInterfaces();
        if (ObjectUtil.isNotEmpty(actualTypeArguments)){
            parameterVo.setCollection(1);

            if (actualTypeArguments[0] instanceof Class) {

                parameterVo.setDataType(((Class) actualTypeArguments[0]).getTypeName());

                if (JavaUtil.isPrimitive((Class) actualTypeArguments[0])) {
                    parameterVo.setIsPrimitive(1);
                }
                parseClass((Class) actualTypeArguments[0], childParameterList);
            }
        }
        if (ObjectUtil.isNotEmpty(childParameterList)){
            parameterVo.setChild(childParameterList);
        }
        parameterList.add(parameterVo);
    }

    public static void parseClassArray(Class clazz,List<ParameterVo> parameterList){
        ParameterVo parameterVo = AnnotationUtil.parseBaseType(clazz);

        List<ParameterVo> childParameterList = new ArrayList<>();
        Class convertClass =clazz.getComponentType();
        if (convertClass!=null){
            if (JavaUtil.isPrimitive(convertClass)) {
                parameterVo.setIsPrimitive(1);
            }
            parseClass(convertClass, childParameterList);
        }

        if (ObjectUtil.isNotEmpty(childParameterList)){
            parameterVo.setChild(childParameterList);
            parameterVo.setCollection(1);
            parameterVo.setDataType(convertClass.getTypeName());
        }
        parameterList.add(parameterVo);
    }

}
