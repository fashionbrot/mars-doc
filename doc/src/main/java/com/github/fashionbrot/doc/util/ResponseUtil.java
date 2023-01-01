package com.github.fashionbrot.doc.util;

import com.github.fashionbrot.doc.annotation.ApiIgnore;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.vo.MethodTypeVo;
import com.github.fashionbrot.doc.vo.ParameterVo;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fashionbrot
 */
public class ResponseUtil {



    public static List<ParameterVo> getResponse2(Method method) {

        List<ParameterVo> list = new ArrayList<>();
        Class<?> returnType = method.getReturnType();
        if (returnType != Void.class) {

            MethodTypeVo methodTypeRoot = MethodUtil.getMethodTypeVo(method);

            if (JavaUtil.isPrimitive(returnType)) {
                String description = "";
                boolean required = false;
                String example = "";
                String dataType = returnType.getTypeName();
                String name = returnType.getSimpleName();

                boolean hidden = false;
                ApiModelProperty apiModelProperty = returnType.getDeclaredAnnotation(ApiModelProperty.class);
                if (apiModelProperty != null) {
                    description = apiModelProperty.value();
                    required = apiModelProperty.required();
                    example = apiModelProperty.example();
                    if (ObjectUtil.isNotEmpty(apiModelProperty.dataType())) {
                        dataType = apiModelProperty.dataType();
                    }
                    hidden = apiModelProperty.hidden();
                }
                ParameterVo build = ParameterVo.builder()
                        .name(name)
                        .description(description)
                        .dataType(dataType)
                        .required(required)
                        .example(example)
                        .build();
                list.add(build);
            } else {
                List<ParameterVo> parameterVos = ParameterUtil.fieldConvertParameter(returnType, methodTypeRoot.getChild(), "");
                if (ObjectUtil.isNotEmpty(parameterVos)) {
                    list.addAll(parameterVos);
                }
            }
        }
        return list;
    }


    public static List<ParameterVo> getResponse(Method method) {

        List<ParameterVo> list = new ArrayList<>();
        Class<?> returnType = method.getReturnType();
        Type genericReturnType = method.getGenericReturnType();
        if (returnType != Void.class) {

            if (JavaUtil.isPrimitive(returnType)) {
                String description = "";
                boolean required = false;
                String example = "";
                String dataType = returnType.getTypeName();
                String name = returnType.getSimpleName();

                boolean hidden = false;
                ApiModelProperty apiModelProperty = returnType.getDeclaredAnnotation(ApiModelProperty.class);
                if (apiModelProperty != null) {
                    description = apiModelProperty.value();
                    required = apiModelProperty.required();
                    example = apiModelProperty.example();
                    if (ObjectUtil.isNotEmpty(apiModelProperty.dataType())) {
                        dataType = apiModelProperty.dataType();
                    }
                    hidden = apiModelProperty.hidden();
                }
                ParameterVo build = ParameterVo.builder()
                        .name(name)
                        .description(description)
                        .dataType(dataType)
                        .required(required)
                        .example(example)
                        .build();
                list.add(build);
            } else {

                setSuperClass(list,returnType);

                List<ParameterVo> parameterVos = ParameterUtil.forFieldOrParam(returnType,genericReturnType,"");
                if (ObjectUtil.isNotEmpty(parameterVos)) {
                    list.addAll(parameterVos);
                }
            }
        }
        return list;
    }


    public static void setSuperClass(List<ParameterVo> list ,Class param){
        Class<?> superclass = param.getSuperclass();
        if (superclass!=null &&  JavaUtil.isNotObject(superclass)){

            setSuperClass(list,superclass);

            Type superClassType = superclass.getGenericSuperclass();

            List<ParameterVo> parameterVos = ParameterUtil.forFieldOrParam(superclass,superClassType,"");
            if (ObjectUtil.isNotEmpty(parameterVos)) {
                list.addAll(parameterVos);
            }
        }
    }


    public static List<ParameterVo> getResponse3(Method method) {
        List<ParameterVo> list = new ArrayList<>();
        Class<?> returnType = method.getReturnType();
        Type genericReturnType = method.getGenericReturnType();
        if(returnType==null || returnType==Void.class){
            return list;
        }

        classSelector(returnType,genericReturnType,list);

        return list;
    }


    public static void classSelector(Class clazz ,Type genericReturnType,List<ParameterVo> parameterList){

        if (JavaUtil.isArray(clazz)) {
            parseClassArray(clazz,genericReturnType,parameterList);
        } else if (JavaUtil.isCollection(clazz)) {
            parseClassList(clazz,genericReturnType,parameterList);
        } else if (JavaUtil.isPrimitive(clazz) || JavaUtil.isMap(clazz) || JavaUtil.isObject(clazz)) {
            parameterList.add(ApiModelPropertyUtil.parseBaseType(clazz));
        }else {
            parseClass(clazz,genericReturnType, parameterList);
        }

    }

    public static void parseClass(Class clazz,Type classType,List<ParameterVo> parameterList){

        parseSuperClass(clazz,parameterList);

        Field[] declaredFields = clazz.getDeclaredFields();
        if (ObjectUtil.isNotEmpty(declaredFields)){
            for (Field field : declaredFields) {
                checkParseField( field , classType, parameterList);
            }
        }
    }

    public static void parseSuperClass(Class propertyClass, List<ParameterVo> parameterList) {

        Class superclass = propertyClass.getSuperclass();
        if (superclass != null && JavaUtil.isNotObject(superclass)) {

            classSelector(superclass,null, parameterList);
        }
    }



    public static void checkParseField( Field field ,Type classType, List<ParameterVo> parameterList) {
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
        fieldSelector( field , classType , parameterList);
    }

    public static void fieldSelector(Field field ,Type classType, List<ParameterVo> parameterList) {

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
            parseFieldArray(field,classType,parameterList);
        } else if (JavaUtil.isCollection(propertyClass)) {
            parseFieldList(field,classType,parameterList);
        } else if (JavaUtil.isPrimitive(propertyClass)) {
            parameterList.add(ApiModelPropertyUtil.parseBaseType(field));
        } else if (JavaUtil.isMap(propertyClass)) {
            parameterList.add(ApiModelPropertyUtil.parseBaseType(field));
        } else if (JavaUtil.isObject(propertyClass)) {
            parseGenericField(field,classType,parameterList);
        } else {
            //class Field 解析
            parseField(field, parameterList);
        }
    }


    public static void parseField(Field field, List<ParameterVo> parameterList) {

        Class<?> propertyClass = field.getType();
        List<ParameterVo> childParameterList = new ArrayList<>();

        //class parent 类解析
        parseSuperClass(propertyClass, childParameterList);

        Field[] declaredFields = propertyClass.getDeclaredFields();
        if (ObjectUtil.isNotEmpty(declaredFields)) {
            ParameterVo parameterVo = ApiModelPropertyUtil.parseBaseType(field);
            for (Field f : declaredFields) {
                parseField( f, childParameterList);
            }
            parameterVo.setChild(childParameterList);
            parameterList.add(parameterVo);
        }
    }





    public static void parseGenericField(Field field, Type classType,List<ParameterVo> parameterList){
        ParameterVo parameterVo = ApiModelPropertyUtil.parseBaseType(field);
        List<ParameterVo> childList =new ArrayList<>();

        Type[] types = TypeUtil.convertActualTypeArguments(classType);
        TypeVariable[] typeVariables = TypeUtil.getTypeVariable(classType);
        if (ObjectUtil.isNotEmpty(types)){
            Type typeByTypeName = MethodUtil.getTypeByTypeName(types, typeVariables, field.getGenericType().getTypeName());
            if (typeByTypeName!=null){

                if (typeByTypeName instanceof Class){
                    Class convertClass = TypeUtil.typeConvertClass(typeByTypeName);
                    if (convertClass!=null){
                        parameterVo.setDataType(convertClass.getTypeName());

                        if (JavaUtil.isArray(convertClass)){
                            parameterVo.setCollection(1);
                            Class componentType = convertClass.getComponentType();
                            if (JavaUtil.isObject(componentType) || JavaUtil.isMap(componentType) || JavaUtil.isPrimitive(componentType)) {
                                parameterVo.setIsPrimitive(1);
                            }else{
                                parseClass(componentType,classType,childList);
                            }

                        }else{
                            if (JavaUtil.isObject(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isPrimitive(convertClass)){
                                parameterVo.setIsPrimitive(1);
                            }else{
                                classSelector(convertClass,classType,childList);
                            }
                        }
                    }
                }else if (typeByTypeName instanceof  ParameterizedType){

                    Class typeConvertClass = TypeUtil.typeConvertClass(typeByTypeName);
                    Type[] convertActualTypeArguments = TypeUtil.convertActualTypeArguments(typeByTypeName);
                    if (ObjectUtil.isNotEmpty(convertActualTypeArguments)){
                        if (convertActualTypeArguments[0] instanceof Class){

                            if (typeConvertClass!=null){

                                if (JavaUtil.isArray(typeConvertClass)) {

                                    parameterVo.setCollection(1);
                                    Class componentType = typeConvertClass.getComponentType();
                                    if (JavaUtil.isObject(componentType) || JavaUtil.isMap(componentType) || JavaUtil.isPrimitive(componentType)) {
                                        parameterVo.setIsPrimitive(1);
                                    } else {
                                        parseClass(componentType, classType, childList);
                                    }
                                }else if (JavaUtil.isCollection(typeConvertClass)){

                                     Class convertClass = TypeUtil.typeConvertClass(convertActualTypeArguments[0]);
                                    if (convertClass!=null){
                                        parameterVo.setDataType(convertClass.getTypeName());
                                        if (JavaUtil.isObject(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isPrimitive(convertClass)){
                                            parameterVo.setIsPrimitive(1);
                                        }else{
                                            parseClass(convertClass,typeByTypeName,childList);
                                        }
                                        if (JavaUtil.isCollection(typeConvertClass)){
                                            parameterVo.setCollection(1);
                                        }
                                    }
                                }else{
//                                    Class convertClass = TypeUtil.typeConvertClass(convertActualTypeArguments[0]);
//                                    if (convertClass!=null){
//                                        parameterVo.setDataType(convertClass.getTypeName());
//                                        if (JavaUtil.isObject(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isPrimitive(convertClass)){
//                                            parameterVo.setIsPrimitive(1);
//                                        }else{
//                                            parseClass(convertClass,typeByTypeName,childList);
//                                        }
//                                        if (JavaUtil.isCollection(typeConvertClass)){
//                                            parameterVo.setCollection(1);
//                                        }
//                                    }


                                    parameterVo.setDataType(typeConvertClass.getTypeName());
                                    if (JavaUtil.isObject(typeConvertClass) || JavaUtil.isMap(typeConvertClass) || JavaUtil.isPrimitive(typeConvertClass)){
                                        parameterVo.setIsPrimitive(1);
                                    }else{
                                        parseClass(typeConvertClass,typeByTypeName,childList);
                                    }
                                    if (JavaUtil.isCollection(typeConvertClass)){
                                        parameterVo.setCollection(1);
                                    }


                                }

                            }
                        }else if (convertActualTypeArguments[0] instanceof ParameterizedType){
                            if (typeByTypeName instanceof Class){
                                Class convertClass = TypeUtil.typeConvertClass(typeByTypeName);
                                if (convertClass!=null){
                                    parameterVo.setDataType(convertClass.getTypeName());
                                    if (JavaUtil.isObject(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isPrimitive(convertClass)){
                                        parameterVo.setIsPrimitive(1);
                                    }else{
                                        parseClass(convertClass,typeByTypeName,childList);
                                    }
                                    if (JavaUtil.isCollection(convertClass)){
                                        parameterVo.setCollection(1);
                                    }
                                }
                            }else if (typeByTypeName instanceof ParameterizedType){
                                Class convertClass = TypeUtil.typeConvertClass(convertActualTypeArguments[0]);
                                if (convertClass!=null){
                                    if (JavaUtil.isCollection(convertClass)){

                                        Class typeConvertClass1 = TypeUtil.typeConvertClass(typeByTypeName);
                                        if (typeConvertClass1!=null){
                                            parameterVo.setDataType(typeConvertClass1.getTypeName());
                                            if (JavaUtil.isObject(typeConvertClass1) || JavaUtil.isMap(typeConvertClass1) || JavaUtil.isPrimitive(typeConvertClass1)){
                                                parameterVo.setIsPrimitive(1);
                                            }else{
                                                parseClass(typeConvertClass1,typeByTypeName,childList);
                                            }
                                            if (JavaUtil.isCollection(typeConvertClass1)){
                                                parameterVo.setCollection(1);
                                            }
                                        }

                                    }else{
                                        parameterVo.setDataType(convertClass.getTypeName());
                                        if (JavaUtil.isObject(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isPrimitive(convertClass)){
                                            parameterVo.setIsPrimitive(1);
                                        }else{
                                            parseClass(convertClass,typeByTypeName,childList);
                                        }
                                        Class typeConvertClass1 = TypeUtil.typeConvertClass(typeByTypeName);
                                        if (JavaUtil.isCollection(typeConvertClass1)){
                                            parameterVo.setCollection(1);
                                        }
                                    }
                                }
                            }

                        }
                    }
//
//                    Class convertClass = TypeUtil.typeConvertClass(typeByTypeName);
//                    if (convertClass!=null){
//                        parameterVo.setDataType(convertClass.getTypeName());
//                        if (JavaUtil.isObject(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isPrimitive(convertClass)){
//                            parameterVo.setIsPrimitive(1);
//                        }else{
//                            classSelector(convertClass,typeByTypeName,childList);
//                        }
//                        if (JavaUtil.isCollection(convertClass)){
//                            parameterVo.setCollection(1);
//                        }
//                    }
                }
            }
        }
        parameterVo.setChild(childList);
        parameterList.add(parameterVo);
    }


    public static void parseFieldList(Field field, Type classType, List<ParameterVo> parameterList) {
        ParameterVo parameterVo = ApiModelPropertyUtil.parseBaseType(field);
        parameterVo.setCollection(1);

        List<ParameterVo> childParameterList = new ArrayList<>();

        Type[] actualTypeArguments = TypeUtil.getActualTypeArguments(field);
        if (ObjectUtil.isNotEmpty(actualTypeArguments)) {

            if (actualTypeArguments[0] instanceof TypeVariable) {

                actualTypeArguments = TypeUtil.convertActualTypeArguments(classType);
                if (ObjectUtil.isNotEmpty(actualTypeArguments)) {

                    if (actualTypeArguments[0] instanceof Class) {
                        Class convertClass = TypeUtil.typeConvertClass(actualTypeArguments[0]);
                        if (convertClass != null) {
                            parameterVo.setDataType(convertClass.getTypeName());
                            if (JavaUtil.isPrimitive(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isObject(convertClass)) {
                                parameterVo.setIsPrimitive(1);
//                                parameterVo.setCollection(null);
                            } else {
                                if (JavaUtil.isPrimitive(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isObject(convertClass)) {
                                    ParameterVo parameterPrimitiveVo = ApiModelPropertyUtil.parseBaseType(convertClass);
                                    parameterPrimitiveVo.setCollection(1);
                                    parameterPrimitiveVo.setIsPrimitive(1);
                                    parameterPrimitiveVo.setDataType(convertClass.getTypeName());
                                    parameterList.add(parameterPrimitiveVo);
                                } else {
//                                    parseClass(convertClass, classType, parameterList);
                                    if (JavaUtil.isArray(convertClass)){
                                        Class componentType = convertClass.getComponentType();
                                        if (JavaUtil.isPrimitive(componentType) || JavaUtil.isMap(componentType) || JavaUtil.isObject(componentType)) {
                                            parameterVo.setIsPrimitive(1);
                                        }else{
                                            parseClass(convertClass.getComponentType(), actualTypeArguments[0], childParameterList);
                                        }
                                    }else{
//                                        parameterVo.setCollection(null);
//                                        parseClass(convertClass, actualTypeArguments[0], parameterList);
                                        parseClass(convertClass, actualTypeArguments[0], childParameterList);
                                    }
                                }
                            }
                        }
                    } else if (actualTypeArguments[0] instanceof ParameterizedType) {
                        Type[] convertActualTypeArguments = TypeUtil.convertActualTypeArguments(actualTypeArguments[0]);
                        if (ObjectUtil.isNotEmpty(convertActualTypeArguments)){

                            if (convertActualTypeArguments[0] instanceof Class){
                                Class convertClass = TypeUtil.typeConvertClass(convertActualTypeArguments[0]);
                                if (convertClass!=null){
                                    parameterVo.setDataType(convertClass.getTypeName());
                                    if (JavaUtil.isPrimitive(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isObject(convertClass)) {
                                        parameterVo.setIsPrimitive(1);
                                    }else{
                                        if (JavaUtil.isPrimitive(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isObject(convertClass)) {
                                            ParameterVo parameterPrimitiveVo = ApiModelPropertyUtil.parseBaseType(convertClass);
                                            parameterPrimitiveVo.setCollection(1);
                                            parameterPrimitiveVo.setIsPrimitive(1);
                                            parameterPrimitiveVo.setDataType(convertClass.getTypeName());
                                            parameterList.add(parameterPrimitiveVo);
                                        }else{
                                            parseClass(convertClass, actualTypeArguments[0], childParameterList);
                                        }
                                    }
                                }

                            }else if (convertActualTypeArguments[0] instanceof ParameterizedType){
                                Class convertClass = TypeUtil.typeConvertClass(convertActualTypeArguments[0]);
                                if (convertClass!=null) {
                                    parseClass(convertClass, convertActualTypeArguments[0], parameterList);
                                }
                            }

//                            Class convertClass = TypeUtil.typeConvertClass(convertActualTypeArguments[0]);
//                            if (convertClass!=null){
//                                parameterVo.setDataType(convertClass.getTypeName());
//                                if (JavaUtil.isPrimitive(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isObject(convertClass)) {
//                                    parameterVo.setIsPrimitive(1);
//                                } else {
//                                    ParameterVo parameterPrimitiveVo = ApiModelPropertyUtil.parseBaseType(convertClass);
//                                    parameterPrimitiveVo.setCollection(1);
//                                    parameterPrimitiveVo.setDataType(convertClass.getTypeName());
//                                    List<ParameterVo> childObjectParameterList=new ArrayList<>();
//                                    if (JavaUtil.isPrimitive(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isObject(convertClass)) {
//
//                                        parameterPrimitiveVo.setIsPrimitive(1);
//
//                                    } else {
////                                        parseClass(convertClass, classType, parameterList);
//
////                                        parseClass(convertClass, actualTypeArguments[0], parameterList);
//                                        parseClass(convertClass, actualTypeArguments[0], childObjectParameterList);
//                                        parameterPrimitiveVo.setChild(childObjectParameterList);
//                                    }
//                                    parameterList.add(parameterPrimitiveVo);
//                                }
//                            }
                        }

                    }
                }
//                    Class convertClass = TypeUtil.typeConvertClass(actualTypeArguments[0]);
//                    if (convertClass!=null){
//                        parameterVo.setDataType(convertClass.getTypeName());
//                        if (JavaUtil.isPrimitive(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isObject(convertClass)){
//                            parameterVo.setIsPrimitive(1);
//                        }else{
//                            if (actualTypeArguments[0] instanceof Class){
////                                parseClass(convertClass,actualTypeArguments[0],childParameterList);
//                                if (JavaUtil.isPrimitive(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isObject(convertClass)){
//                                    ParameterVo parameterPrimitiveVo = ApiModelPropertyUtil.parseBaseType(convertClass);
//                                    parameterPrimitiveVo.setCollection(1);
//                                    parameterPrimitiveVo.setIsPrimitive(1);
//                                    parameterPrimitiveVo.setDataType(convertClass.getTypeName());
//                                    parameterList.add(parameterPrimitiveVo);
//                                }else{
//                                    parseClass(convertClass, classType, parameterList);
//                                }
//
//                            }else if (actualTypeArguments[0] instanceof ParameterizedType) {
//                                classSelector(convertClass, actualTypeArguments[0], childParameterList);
//                            }
//
//                            Type[] convertActualTypeArguments = TypeUtil.convertActualTypeArguments(actualTypeArguments[0]);
//                            if (ObjectUtil.isNotEmpty(convertActualTypeArguments) && convertActualTypeArguments[0] instanceof Class){
//                                parameterVo.setDataType(convertActualTypeArguments[0].getTypeName());
//                            }
//                        }
//                    }
//                }

            } else if (actualTypeArguments[0] instanceof Class) {

                Class convertClass = TypeUtil.typeConvertClass(actualTypeArguments[0]);
                if (convertClass!=null) {
                    parameterVo.setDataType(convertClass.getTypeName());
                    if (JavaUtil.isPrimitive(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isObject(convertClass)) {
                        parameterVo.setIsPrimitive(1);
                    } else {
                        classSelector((Class) actualTypeArguments[0], classType, childParameterList);
                    }
                }
            }

        }

        if (ObjectUtil.isNotEmpty(childParameterList)){
            parameterVo.setChild(childParameterList);
        }
        parameterList.add(parameterVo);
    }

    public static void parseFieldArray(Field field,Type classType,List<ParameterVo> parameterList){
        ParameterVo parameterVo = ApiModelPropertyUtil.parseBaseType(field);

        List<ParameterVo> childParameterList = new ArrayList<>();
        Class convertClass =field.getType().getComponentType();
        if (convertClass!=null){
            parameterVo.setCollection(1);
            parameterVo.setDataType(convertClass.getTypeName());
            if (JavaUtil.isPrimitive(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isObject(convertClass)){
                parameterVo.setIsPrimitive(1);
            }else{
                parseClass(convertClass,classType, childParameterList);
            }
        }

        if (ObjectUtil.isNotEmpty(childParameterList)){
            parameterVo.setChild(childParameterList);
        }
        parameterList.add(parameterVo);
    }


    public static void parseClassList(Class clazz,Type classType,List<ParameterVo> parameterList){
        if (classType == null) {
            return;
        }
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

                        ParameterVo parameterVo = ApiModelPropertyUtil.parseBaseType(clazz);
                        parameterVo.setCollection(1);
                        parameterVo.setDataType(convertClass.getTypeName());

                        if (JavaUtil.isPrimitive(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isObject(convertClass)){
                            parameterVo.setIsPrimitive(1);
                        }else{
                            List<ParameterVo> childList=new ArrayList<>();
                            parseClass(convertClass, classType, childList);
                            parameterVo.setChild(childList);
                        }
                        parameterList.add(parameterVo);
                    }

                }else if (actualTypeArguments[0] instanceof ParameterizedType){
                    classSelector((Class) actualTypeArguments[0], classType, parameterList);
                }
            }
        }
    }
    public static void parseClassArray(Class clazz,Type classType,List<ParameterVo> parameterList){
        Class convertClass =clazz.getComponentType();
        if (convertClass!=null){
            if (classType instanceof Class){

                ParameterVo parameterVo = ApiModelPropertyUtil.parseBaseType(clazz);
                parameterVo.setCollection(1);
                parameterVo.setDataType(convertClass.getTypeName());
                parameterVo.setName(convertClass.getTypeName()+"[]");
                if (JavaUtil.isPrimitive(convertClass) || JavaUtil.isMap(convertClass) || JavaUtil.isObject(convertClass)){
                    parameterVo.setIsPrimitive(1);
                }else {
                    List<ParameterVo> childList=new ArrayList<>();
                    parseClass(convertClass, classType, childList);
                    parameterVo.setChild(childList);
                }

                parameterList.add(parameterVo);
            }

        }
    }

}
