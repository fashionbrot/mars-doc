package com.github.fashionbrot.doc.util;

import java.lang.reflect.*;

/**
 * @author fashionbrot
 */
public class TypeUtil {


    public static Type[] getActualTypeArguments(Parameter parameter){
        Type parameterizedType = parameter.getParameterizedType();
        if (parameterizedType!=null){
            return MethodUtil.convertActualTypeArguments(parameter.getParameterizedType());
        }
        return null;
    }
    public static Type[] getActualTypeArguments(Field field){
        Type parameterizedType = field.getGenericType();
        if (parameterizedType!=null){
            return MethodUtil.convertActualTypeArguments(field.getGenericType());
        }
        return null;
    }

    public static Type[] convertActualTypeArguments(Type type){
        if (type!=null){
            if ( type instanceof ParameterizedType) {
                return ((ParameterizedType) type).getActualTypeArguments();
            }else if (type instanceof Class){

            }
        }
        return null;
    }



    public static TypeVariable[] getTypeVariable(Parameter parameter){
        Type parameterizedType = parameter.getParameterizedType();
        if (parameterizedType!=null){
            return getTypeVariable(parameterizedType);
        }
        return null;
    }

    public static TypeVariable[] getTypeVariable(Field field){
        Type parameterizedType = field.getGenericType();
        if (parameterizedType!=null){
            return getTypeVariable(parameterizedType);
        }
        return null;
    }

    public static TypeVariable[] getTypeVariable(Type type){
        Class typeClass = null;
        if(type instanceof  Class){
            typeClass = (Class) type;
        }else if (type instanceof ParameterizedType){
            typeClass = (Class) ((ParameterizedType) type).getRawType();
        }
        if (typeClass!=null){
            return typeClass.getTypeParameters();
        }
        return null;
    }

    public static Class typeConvertClass(Type type) {
        Class typeClass = null;
        if(type instanceof  Class){
            typeClass = (Class) type;
        }else if (type instanceof ParameterizedType){
            typeClass = (Class) ((ParameterizedType) type).getRawType();
        }
        return typeClass;
    }

}
