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

                Class typeClass = TypeUtil.typeConvertClass(classType);

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
                .typeClass(TypeUtil.typeConvertClass(genericReturnType))
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
        TypeVariable<? extends Class<?>>[] typeVariables = TypeUtil.getTypeVariable(classType);

//        TypeVariable<?>[] typeParameters = ((ParameterizedType) classType).getRawType().getClass().getTypeParameters();
//        TypeVariable<?>[] typeVariables = ((TypeVariable) genType).getGenericDeclaration().getTypeParameters();
//        TypeVariable<?>[] typeParameters1 = genType.getClass().getTypeParameters();
        Type[] actualTypeArguments = ((ParameterizedType) classType).getActualTypeArguments();
        if (ObjectUtil.isNotEmpty(typeVariables)){
            for (int i = 0; i < typeVariables.length; i++) {
                Type typeVariable = typeVariables[i];
                Type type = actualTypeArguments[i];

                Class typeClass = TypeUtil.typeConvertClass(type);

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







}
