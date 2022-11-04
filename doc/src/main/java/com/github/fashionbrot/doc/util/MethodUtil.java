package com.github.fashionbrot.doc.util;


import java.lang.reflect.Method;

/**
 * @author fashi
 */
public class MethodUtil {


    public static String getMethodId(Method method){
        Class<?> declaringClass = method.getDeclaringClass();
        return declaringClass.getName()+"#"+method.getName();
    }

}
