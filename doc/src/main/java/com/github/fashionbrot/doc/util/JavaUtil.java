package com.github.fashionbrot.doc.util;



import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;


public class JavaUtil {

    public static Properties javaPrimitive = new Properties();
    public static Properties javaMap = new Properties();
    public static Properties mvcIgnoreParam = new Properties();
    public static Properties mvcFile = new Properties();

    static {
        javaPrimitive = ResourceUtil.getResourceAsProperties("mars-java-primitive.properties");
        javaMap = ResourceUtil.getResourceAsProperties("mars-java-map.properties");
        mvcIgnoreParam = ResourceUtil.getResourceAsProperties("mars-mvc-ignore-param.properties");
        mvcFile = ResourceUtil.getResourceAsProperties("mars-mvc-file.properties");
    }

    /**
     * Check if it is the basic data type of json data
     *
     * @param clazz clazz
     * @return boolean
     */
    public static boolean isPrimitive(Class clazz) {
        return javaPrimitive.containsKey(clazz.getTypeName());
    }

    public static boolean isNotPrimitive(Class clazz) {
        return !isPrimitive(clazz);
    }

    /**
     * Check if it is the basic data type of json data
     *
     * @param typeName clazz typeName
     * @return boolean
     */
    public static boolean isPrimitive(String typeName) {
        return javaPrimitive.containsKey(typeName);
    }

    public static boolean isNotPrimitive(String typeName) {
        return !isPrimitive(typeName);
    }

    /**
     * validate java collection
     *
     * @param  clazz  clazz
     * @return boolean
     */
    public static boolean isCollection(Class clazz) {
        return clazz!=null && Iterable.class.isAssignableFrom(clazz);
    }

    public static boolean isObject(Class clazz){
        return "java.lang.Object".equals(clazz.getTypeName());
    }

    public static boolean isNotObject(Class clazz){
        if (clazz==null){
            return false;
        }
        return !isObject(clazz);
    }

    public static boolean isBaseType(Class clazz){
        if (clazz==null){
            return false;
        }
        return JavaUtil.isNotPrimitive(clazz.getTypeName());
    }

    public static boolean isMap(Class clazz){
        return isMap(clazz.getTypeName());
    }
    /**
     * Check if it is an map
     *
     * @param type java type
     * @return boolean
     */
    public static boolean isMap(String type) {
        return javaMap.containsKey(type);
    }

    /**
     * check array
     *
     * @param type type name
     * @return boolean
     */
    public static boolean isArray(String type) {
        return type.endsWith("[]");
    }

    /**
     * check array
     *
     * @param clazz type name
     * @return boolean
     */
    public static boolean isArray(Class clazz) {
        return clazz!=null && clazz.isArray();
    }



    /**
     * Download
     *
     * @param typeName return type name
     * @return boolean
     */
    public static boolean isFileDownloadResource(String typeName) {
        switch (typeName) {
            case "org.springframework.core.io.Resource":
            case "org.springframework.core.io.InputStreamSource":
            case "org.springframework.core.io.ByteArrayResource":
            case "org.noear.solon.core.handle.DownloadedFile":
                return true;
            default:
                return false;
        }
    }

    /**
     * ignore param of spring mvc
     *
     * @param paramType    param type name
     * @return boolean
     */
    public static boolean isMvcIgnoreParams(String paramType) {
        if (ObjectUtil.isEmpty(paramType)) {
            return false;
        }
        return mvcIgnoreParam.containsKey(paramType);
    }

    /**
     * ignore field type name
     *
     * @param typeName field type name
     * @return String
     */
    public static boolean isIgnoreFieldTypes(String typeName) {
        switch (typeName) {
            case "org.slf4j.Logger":
            case "org.apache.ibatis.logging.Log":
            case "java.lang.Class":
                return true;
            default:
                return false;
        }
    }

    /**
     * check file
     *
     * @param typeName type name
     * @return boolean
     */
    public static boolean isFile(String typeName) {
        if (ObjectUtil.isEmpty(typeName)){
            return false;
        }
        return mvcFile.containsKey(typeName);
    }





    public static boolean isFinal(Field field) {
        if (Modifier.isFinal(field.getModifiers())) {
            return true;
        }
        field.setAccessible(true);
        return false;
    }

    public static boolean isNotFinal(Field field){
        return !isFinal(field);
    }

}
