package com.github.fashionbrot.doc.util;

import com.github.fashionbrot.doc.annotation.ApiImplicitParam;
import com.github.fashionbrot.doc.enums.ParamTypeEnum;
import com.github.fashionbrot.doc.vo.ParameterVo;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fashionbrot
 */
@Slf4j
public class RequestUtil {

    public static final String REQUEST_BODY = "org.springframework.web.bind.annotation.RequestBody";
    public static final String REQUEST_BODY_REQUIRED = "required";



    public static List<ParameterVo> getRequest(Method method) {
        List<ParameterVo> parameterList = new ArrayList<>();
        List<ParameterVo> apiImplicitParamList = parseApiImplicitParam(method);

        try {
            Parameter[] parameterArray = method.getParameters();
            if (ObjectUtil.isNotEmpty(parameterArray)) {
                for (int i = 0; i < parameterArray.length; i++) {
                    Parameter parameter = parameterArray[i];
                    parameterSelector(parameter, parameterList);
                }
            }
        }catch (Throwable e){
            log.error("requestUtil getRequest error",e);
        }

        if (ObjectUtil.isNotEmpty(apiImplicitParamList)) {
            parameterList.addAll(apiImplicitParamList);
        }
        return parameterList;
    }

    public static boolean isRequestBody(Parameter parameter) {
        return getRequestBodyRequired(parameter);
    }

    public static void parameterSelector(Parameter parameter, List<ParameterVo> parameterList) {
        if (JavaUtil.isMvcIgnoreParams(parameter.getType().getTypeName()) || AnnotationUtil.isIgnore(parameter)) {
            return;
        }
        String requestType = ParamTypeEnum.QUERY.name();
        if (isRequestBody(parameter)) {
            requestType = ParamTypeEnum.BODY.name();
        }
        Class<?> propertyClass = parameter.getType();

        if (JavaUtil.isArray(propertyClass)) {
            //数组类型
            parseParameterArray(parameter, requestType, parameterList);
        } else if (JavaUtil.isCollection(propertyClass)) {
            //集合类型
            parseParameterList(parameter, requestType, parameterList);
        } else if (JavaUtil.isPrimitive(propertyClass) || JavaUtil.isObject(propertyClass) || JavaUtil.isMap(propertyClass)) {
            //基本类型
            ParameterVo parameterVo = AnnotationUtil.parseBaseType(parameter);
            parameterVo.setRequestType(requestType);
            parameterList.add(parameterVo);
        } else {
            //解析Class
            parseParameterField(parameter, requestType, parameterList);
        }
    }

    public static void fieldSelector(Class clazz,Field field, String requestType, List<ParameterVo> parameterList) {
        if (JavaUtil.isFinal(field) || AnnotationUtil.isIgnore(field)) {
            return;
        }
        Class<?> propertyClass = field.getType();

        if (JavaUtil.isArray(propertyClass)) {
            parseFieldArray(field, requestType, parameterList);
        } else if (JavaUtil.isCollection(propertyClass)) {
            parseFieldList(field, requestType, parameterList);
        } else if (JavaUtil.isPrimitive(propertyClass)) {
            ParameterVo parameterVo = AnnotationUtil.parseBaseType(field);
            parameterVo.setRequestType(requestType);
            parameterList.add(parameterVo);
        } else if (JavaUtil.isObject(propertyClass) || JavaUtil.isMap(propertyClass)) {
            ParameterVo parameterVo = AnnotationUtil.parseBaseType(field);
            parameterVo.setRequestType(requestType);
            parameterList.add(parameterVo);
        } else {
            //class Field 解析
            parseClassField(field, requestType, parameterList);
        }
    }

    public static void parseParameterField(Parameter parameter, String requestType, List<ParameterVo> parameterList) {
        ParameterVo parameterVo = AnnotationUtil.parseBaseType(parameter);
        parameterVo.setRequestType(requestType);
        List<ParameterVo> childList = new ArrayList<>();

        Class<?> typeClass = parameter.getType();
        String parameterName = parameter.getName();
        /**
         * class parent 类解析
         */
        parseSuperClass(typeClass, requestType, childList);

        Field[] declaredFields = typeClass.getDeclaredFields();
        if (ObjectUtil.isNotEmpty(declaredFields)) {
            for (Field field : declaredFields) {
                fieldSelector(typeClass,field, requestType, childList);
            }
            parameterVo.setChild(childList);
            parameterList.add(parameterVo);
        }
    }

    public static void parseClassField(Field field, String requestType, List<ParameterVo> parameterList) {
        ParameterVo parameterVo = AnnotationUtil.parseBaseType(field);
        parameterVo.setRequestType(requestType);
        List<ParameterVo> childList = new ArrayList<>();


        Class<?> typeClass = field.getType();
        /**
         * class parent 类解析
         */
        parseSuperClass(typeClass, requestType, parameterList);

        Field[] declaredFields = typeClass.getDeclaredFields();
        if (ObjectUtil.isNotEmpty(declaredFields)) {
            for (Field f : declaredFields) {
                fieldSelector(typeClass,f, requestType, childList);
            }
            parameterVo.setChild(childList);
            parameterList.add(parameterVo);
        }
    }

    public static void parseClassField(Class clazz, String requestType, List<ParameterVo> parameterList) {


        parseSuperClass(clazz, requestType, parameterList);

        Field[] declaredFields = clazz.getDeclaredFields();
        if (ObjectUtil.isNotEmpty(declaredFields)) {
            for (Field field : declaredFields) {



                fieldSelector(clazz,field, requestType, parameterList);
            }
        }
    }

    public static void parseSuperClass(Class clazz, String requestType, List<ParameterVo> parameterList) {
        Class superclass = clazz.getSuperclass();
        if (superclass != null && JavaUtil.isNotObject(superclass)) {

            /**
             * class Field 解析
             */
            parseClassField(superclass, requestType, parameterList);
        }
    }

    public static void parseFieldList(Field field, String requestType, List<ParameterVo> parameterList) {
        ParameterVo parameterVo = AnnotationUtil.parseBaseType(field);
        parameterVo.setRequestType(requestType);
        List<ParameterVo> childParameterList = new ArrayList<>();
        Type[] actualTypeArguments = TypeUtil.getActualTypeArguments(field);
        if (ObjectUtil.isNotEmpty(actualTypeArguments)) {
            parameterVo.setCollection(1);
            if (actualTypeArguments[0] instanceof Class) {
                Class typeConvertClass = TypeUtil.typeConvertClass(actualTypeArguments[0]);
                if (typeConvertClass!=null){
                    parameterVo.setDataType(typeConvertClass.getTypeName());
                    if (JavaUtil.isPrimitive(typeConvertClass)) {
                        parameterVo.setIsPrimitive(1);
                    } else {
                        parseClassField((Class) actualTypeArguments[0], requestType, childParameterList);
                    }
                }

            }
        }
        if (ObjectUtil.isNotEmpty(childParameterList)) {
            parameterVo.setChild(childParameterList);
        }
        parameterList.add(parameterVo);
    }

    public static void parseFieldArray(Field field, String requestType, List<ParameterVo> parameterList) {
        ParameterVo parameterVo = AnnotationUtil.parseBaseType(field);
        parameterVo.setRequestType(requestType);
        List<ParameterVo> childParameterList = new ArrayList<>();
        Class convertClass = field.getType().getComponentType();
        if (convertClass != null) {
            parameterVo.setCollection(1);
            parameterVo.setDataType(convertClass.getTypeName());
            if (JavaUtil.isPrimitive(convertClass)) {
                parameterVo.setIsPrimitive(1);
            } else {
                parseClassField(convertClass, requestType, childParameterList);
            }
        }

        if (ObjectUtil.isNotEmpty(childParameterList)) {
            parameterVo.setChild(childParameterList);
        }
        parameterList.add(parameterVo);
    }


    public static void parseParameterArray(Parameter parameter, String requestType, List<ParameterVo> parameterList) {
        ParameterVo parameterVo = AnnotationUtil.parseBaseType(parameter);
        parameterVo.setRequestType(requestType);
        List<ParameterVo> childParameterList = new ArrayList<>();
        Class convertClass = parameter.getType().getComponentType();
        if (convertClass != null) {
            parameterVo.setCollection(1);
            parameterVo.setDataType(convertClass.getTypeName());
            if (JavaUtil.isPrimitive(convertClass)) {
                parameterVo.setIsPrimitive(1);
            } else {
                parseClassField(convertClass, requestType, childParameterList);
            }
        }
        if (ObjectUtil.isNotEmpty(childParameterList)) {
            parameterVo.setChild(childParameterList);
        }

        parameterList.add(parameterVo);
    }

    public static void parseParameterList(Parameter parameter, String requestType, List<ParameterVo> parameterList) {
        ParameterVo parameterVo = AnnotationUtil.parseBaseType(parameter);
        parameterVo.setRequestType(requestType);
        List<ParameterVo> childParameterList = new ArrayList<>();
        Type[] actualTypeArguments = TypeUtil.getActualTypeArguments(parameter);
        if (ObjectUtil.isNotEmpty(actualTypeArguments)) {
            parameterVo.setCollection(1);
            if (actualTypeArguments[0] instanceof Class) {
                parameterVo.setDataType(((Class) actualTypeArguments[0]).getTypeName());
                if (JavaUtil.isPrimitive((Class) actualTypeArguments[0])) {
                    parameterVo.setIsPrimitive(1);
                } else {
                    parseClassField((Class) actualTypeArguments[0], requestType, childParameterList);
                }
            }
        }
        if (ObjectUtil.isNotEmpty(childParameterList)) {
            parameterVo.setChild(childParameterList);
        }
        parameterList.add(parameterVo);
    }


    public static List<ParameterVo> parseApiImplicitParam(Method method) {
        ApiImplicitParam[] apiImplicitParams = method.getDeclaredAnnotationsByType(ApiImplicitParam.class);
        if (ObjectUtil.isNotEmpty(apiImplicitParams)) {
            return Arrays.stream(apiImplicitParams)
                    .filter(m -> checkParamType(m.paramType()))
                    .map(m -> buildParameterVo(m))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public static boolean checkParamType(String paramType) {
        if (ParamTypeEnum.BODY.name().equalsIgnoreCase(paramType) ||
                ParamTypeEnum.QUERY.name().equalsIgnoreCase(paramType)) {
            return true;
        }
        return false;
    }

    public static ParameterVo buildParameterVo(ApiImplicitParam param) {
        return ParameterVo.builder()
                .name(param.name())
                .description(param.value())
                .requestType(param.paramType())
                .required(param.required())
                .dataType(param.dataType())
                .example(param.defaultValue())
                .multiple(param.multiple())
                .build();
    }


    private static boolean getRequestBodyRequired(Parameter parameter) {

        Optional<Annotation> requestBodyAnnotation = Arrays.stream(parameter.getDeclaredAnnotations()).filter(m -> REQUEST_BODY.equals(m.annotationType().getTypeName())).findFirst();
        if (requestBodyAnnotation.isPresent()) {
            Annotation annotation = requestBodyAnnotation.get();
            Optional<Method> requestBodyRequiredMethod = Arrays.stream(annotation.annotationType().getDeclaredMethods()).filter(m -> REQUEST_BODY_REQUIRED.equals(m.getName())).findFirst();
            if (requestBodyRequiredMethod.isPresent()) {
                return getRequestBodyRequired(annotation, requestBodyRequiredMethod.get());
            }
        }
        return false;
    }

    public static boolean getRequestBodyRequired(Annotation annotation, Method method) {
        Object invoke = null;
        try {
            invoke = method.invoke(annotation, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (invoke instanceof Boolean) {
            return (Boolean) invoke;
        }
        return true;
    }

}
