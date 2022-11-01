package com.github.fashionbrot;

import com.github.fashionbrot.entity.TestEntity;
import com.github.fashionbrot.vo.RespVo;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class MethodTest {




    public static void main(String[] args) throws NoSuchMethodException {

        Method method = MethodTest.class.getMethod("test", null);
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        for (Type genericParameterType : genericParameterTypes) {
            System.out.println("type:"+genericParameterType);
            //ParameterizedType:表示一种参数化类型，比如Collection<Object>
            if(genericParameterType instanceof ParameterizedType){
                Type[] actualTypeArguments = ((ParameterizedType) genericParameterType).getActualTypeArguments();
                for (Type parameterType : actualTypeArguments) {
                    System.out.println(parameterType);
                }
            }
        }

        Type genericReturnType = method.getGenericReturnType();
        if(genericReturnType instanceof ParameterizedType){
            Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
            for (Type parameterType : actualTypeArguments) {

                System.out.println(parameterType);
                System.out.println(toType(parameterType)[1]);
            }
        }


    }

    public RespVo<List<TestEntity>> test(){
        return RespVo.success();
    }

    public static Type[] toType(Type type){
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        return actualTypeArguments;
    }

}
