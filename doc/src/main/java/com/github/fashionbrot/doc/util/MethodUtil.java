package com.github.fashionbrot.doc.util;


import com.github.fashionbrot.doc.consts.MarsDocConst;
import com.github.fashionbrot.doc.enums.ParameterizedTypeEnum;
import com.github.fashionbrot.doc.type.DocParameterizedType;
import com.github.fashionbrot.doc.vo.MethodTypeVo;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fashi
 */
public class MethodUtil {


    public static String getMethodId(Method method){
        Class<?> declaringClass = method.getDeclaringClass();
        return declaringClass.getName()+"#"+method.getName();
    }


    public static MethodTypeVo getMethodTypeVo(Method method){
        String methodId = MethodUtil.getMethodId(method);

        Type genericReturnType = method.getGenericReturnType();
        TypeVariable<? extends Class<?>>[] typeParameters = method.getReturnType().getTypeParameters();

        List<MethodTypeVo> methodTypeVoList= new ArrayList<>();

        if (ObjectUtil.isNotEmpty(typeParameters)){
            Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
            for (int i = 0; i < typeParameters.length; i++) {
                Type  classType = actualTypeArguments[i];
                Type  genType = typeParameters[i];

                Class typeClass = typeConvertClass(classType);

                MethodTypeVo build = MethodTypeVo.builder()
                        .methodId(methodId)
                        .typeClass(typeClass)
                        .typeClassStr(classType.getTypeName())
                        .typeName(genType.getTypeName())
                        .build();


                if (JavaUtil.isNotPrimitive(classType.getTypeName())){

                    if (typeClass.isArray()){

                        DocParameterizedType parameterizedType = new DocParameterizedType(List.class, new Class[]{typeClass.getComponentType()},null);
                        List<MethodTypeVo> childList = getMethodTypeList(parameterizedType, genType, methodId);

                        build.setChild(childList);
                    }else {

                        List<MethodTypeVo> childList = getMethodTypeList(classType, genType, methodId);
                        build.setChild(childList);

                    }

                }
                methodTypeVoList.add(build);
            }
        }
        MethodTypeVo root = MethodTypeVo.builder()
                .methodId(methodId)
                .typeClass(typeConvertClass(genericReturnType))
                .typeName(genericReturnType.getTypeName())
                .typeClassStr(MarsDocConst.TYPE_CLASS_ROOT)
                .child(methodTypeVoList)
                .build();
        return root;
    }



    public static List<MethodTypeVo> getMethodTypeList(Type classType,Type  genType,String methodId){
        List<MethodTypeVo> list=new ArrayList<>();


        if (!ParameterizedTypeEnum.isParameterizedType(classType.getClass().getName())  ){
            return list;
        }
//        TypeVariable<? extends Class<?>>[] typeVariables = ((ParameterizedTypeImpl) classType).getRawType().getTypeParameters();
        TypeVariable<? extends Class<?>>[] typeVariables = getTypeVariable(classType);

//        TypeVariable<?>[] typeParameters = ((ParameterizedType) classType).getRawType().getClass().getTypeParameters();
//        TypeVariable<?>[] typeVariables = ((TypeVariable) genType).getGenericDeclaration().getTypeParameters();
//        TypeVariable<?>[] typeParameters1 = genType.getClass().getTypeParameters();
        Type[] actualTypeArguments = ((ParameterizedType) classType).getActualTypeArguments();
        if (ObjectUtil.isNotEmpty(typeVariables)){
            for (int i = 0; i < typeVariables.length; i++) {
                Type typeVariable = typeVariables[i];
                Type type = actualTypeArguments[i];

                Class typeClass = typeConvertClass(type);

                MethodTypeVo build = MethodTypeVo.builder()
                        .methodId(methodId)
                        .typeClassStr(type.getTypeName())
                        .typeClass(typeClass)
                        .typeName(typeVariable.getTypeName())
                        .build();

                if (JavaUtil.isNotPrimitive(typeClass.getTypeName())){
                    if (typeClass.isArray()){
                        DocParameterizedType parameterizedType = new DocParameterizedType(List.class, new Class[]{typeClass.getComponentType()},null);
                        List<MethodTypeVo> childList = getMethodTypeList(parameterizedType, genType, methodId);
                        build.setChild(childList);
                    }else {
                        build.setChild(getMethodTypeList(type, typeVariable, methodId));
                    }
                }
                list.add(build);
            }
        }
        return list;
    }

    public static TypeVariable[] getTypeVariable(Parameter parameter){
        Type parameterizedType = parameter.getParameterizedType();
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


    public static Type[] getActualTypeArguments(Parameter parameter){
        Type parameterizedType = parameter.getParameterizedType();
        if (parameterizedType!=null){
            return MethodUtil.convertActualTypeArguments(parameter.getParameterizedType());
        }
        return null;
    }

    public static Type[] convertActualTypeArguments(Type type){
        if (type!=null && type instanceof ParameterizedType){
            return ((ParameterizedType) type).getActualTypeArguments();
        }
        return null;
    }

    public static Integer getTypeVariableIndex(TypeVariable<?>[] typeVariables, TypeVariable typeVariable) {
        if (ObjectUtil.isNotEmpty(typeVariables)) {
            for (int i = 0; i < typeVariables.length; i++) {
                if (typeVariables[i] == typeVariable) {
                    return i;
                }
            }
        }
        return null;
    }

    public static Integer getTypeVariableIndex(TypeVariable<?>[] typeVariables, String fieldTypeName) {
        if (ObjectUtil.isNotEmpty(typeVariables)) {
            for (int i = 0; i < typeVariables.length; i++) {
                TypeVariable<?> typeVariable = typeVariables[i];
                if (typeVariable.getTypeName().equals(fieldTypeName)) {
                    return i;
                }
            }
        }
        return null;
    }

    public static Type getTypeByTypeName(Type[] types, TypeVariable<?>[] typeVariables, String fieldTypeName) {
        if (ObjectUtil.isNotEmpty(types) && ObjectUtil.isNotEmpty(typeVariables)) {
            Integer typeVariableIndex = getTypeVariableIndex(typeVariables, fieldTypeName);
            if (typeVariableIndex != null) {
                Type type = types[typeVariableIndex];
                return type;
            }
        }
        return null;
    }

    public static Type getTypeByTypeName(Type[] types, TypeVariable<?>[] typeVariables, TypeVariable typeVariable) {
        if (ObjectUtil.isNotEmpty(types)) {
            Integer typeVariableIndex = getTypeVariableIndex(typeVariables, typeVariable);
            if (typeVariableIndex != null) {
                Type type = types[typeVariableIndex];
                return type;
            }
        }
        return null;
    }
}
