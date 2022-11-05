package com.github.fashionbrot.doc.util;



import com.github.fashionbrot.doc.consts.SpringMvcAnnotations;

import java.util.List;
import java.util.Objects;


public class JavaClassValidateUtil {

    private static String CLASS_PATTERN = "^([A-Za-z]{1}[A-Za-z\\d_]*\\.)+[A-Za-z][A-Za-z\\d_]*$";

    /**
     * Check if it is the basic data array type of json data
     *
     * @param type0 java class name
     * @return boolean
     */
    public static boolean isPrimitiveArray(String type0) {
        String type = type0.contains("java.lang") ? type0.substring(type0.lastIndexOf(".") + 1) : type0;
        type = type.toLowerCase();
        switch (type) {
            case "integer[]":
            case "void":
            case "int[]":
            case "long[]":
            case "double[]":
            case "float[]":
            case "short[]":
            case "bigdecimal[]":
            case "char[]":
            case "string[]":
            case "boolean[]":
            case "byte[]":
                return true;
            default:
                return false;
        }
    }

    /**
     * Check if it is the basic data type of json data
     *
     * @param type0 java class name
     * @return boolean
     */
    public static boolean isPrimitive(String type0) {
        if (Objects.isNull(type0)) {
            return true;
        }
        String type = type0.contains("java.lang") ? type0.substring(type0.lastIndexOf(".") + 1) : type0;
        type = type.toLowerCase();
        switch (type) {
            case "integer":
            case "void":
            case "int":
            case "long":
            case "double":
            case "float":
            case "short":
            case "bigdecimal":
            case "char":
            case "string":
            case "number":
            case "boolean":
            case "byte":
            case "uuid":
            case "character":
            case "java.sql.timestamp":
            case "java.util.date":
            case "java.time.localdatetime":
            case "java.time.localtime":
            case "java.time.year":
            case "java.time.yearmonth":
            case "java.time.monthday":
            case "java.time.period":
            case "localdatetime":
            case "localdate":
            case "zoneddatetime":
            case "offsetdatetime":
            case "period":
            case "java.time.localdate":
            case "java.time.zoneddatetime":
            case "java.time.offsetdatetime":
            case "java.math.bigdecimal":
            case "java.math.biginteger":
            case "java.util.uuid":
            case "java.io.serializable":
            case "java.lang.character":
            case "org.bson.types.objectid":
                return true;
            default:
                return false;
        }
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

    /**
     * Check if it is an map
     *
     * @param type java type
     * @return boolean
     */
    public static boolean isMap(String type) {
        switch (type) {
            case "java.util.Map":
            case "java.util.SortedMap":
            case "java.util.TreeMap":
            case "java.util.LinkedHashMap":
            case "java.util.HashMap":
            case "java.util.concurrent.ConcurrentHashMap":
            case "java.util.concurrent.ConcurrentMap":
            case "java.util.Properties":
            case "java.util.Hashtable":
                return true;
            default:
                return false;
        }
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
     * ignore tag request field
     *
     * @param tagName custom field tag
     * @return boolean
     */
    public static boolean isIgnoreTag(String tagName) {
        switch (tagName) {
            case "ignore":
                return true;
            default:
                return false;
        }
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
     * @param ignoreParams ignore param list
     * @return boolean
     */
    public static boolean isMvcIgnoreParams(String paramType, List<String> ignoreParams) {
        if (ObjectUtil.isNotEmpty(ignoreParams) && ignoreParams.contains(paramType)) {
            return true;
        }
        switch (paramType) {
            case "org.springframework.ui.Model":
            case "org.springframework.ui.ModelMap":
            case "org.springframework.web.servlet.ModelAndView":
            case "org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap":
            case "org.springframework.validation.BindingResult":
            case "javax.servlet.http.HttpServletRequest":
            case "javax.servlet.http.HttpServlet":
            case "javax.servlet.http.HttpSession":
            case "javax.servlet.http.HttpServletResponse":
            case "org.springframework.web.context.request.WebRequest":
            case "org.springframework.web.reactive.function.server.ServerRequest":
            case "org.springframework.web.multipart.MultipartHttpServletRequest":
            case "org.springframework.http.HttpHeaders":
            case "org.springframework.core.io.Resource":
            case "org.springframework.core.io.InputStreamSource":
            case "org.springframework.core.io.ByteArrayResource":
                return true;
            default:
                return false;
        }
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
        switch (typeName) {
            case "org.springframework.web.multipart.MultipartFile":
            case "org.springframework.web.multipart.MultipartFile[]":
            case "java.util.List<org.springframework.web.multipart.MultipartFile>":
            case "org.springframework.web.multipart.commons.CommonsMultipartFile":
            case "org.springframework.web.multipart.commons.CommonsMultipartFile[]":
            case "java.util.List<org.springframework.web.multipart.commons.CommonsMultipartFile>":
            case "javax.servlet.http.Part":
            case "javax.servlet.http.Part[]":
            case "java.util.List<javax.servlet.http.Part>":
                return true;
            default:
                return false;
        }
    }



    /**
     * ignore param with annotation
     *
     * @param annotation Spring Mvc's annotation
     * @return boolean
     */
    public static boolean ignoreSpringMvcParamWithAnnotation(String annotation) {
        switch (annotation) {
            case SpringMvcAnnotations.SESSION_ATTRIBUTE:
            case SpringMvcAnnotations.REQUEST_ATTRIBUTE:
            case SpringMvcAnnotations.REQUEST_HERDER:
                return true;
            default:
                return false;
        }
    }




}
