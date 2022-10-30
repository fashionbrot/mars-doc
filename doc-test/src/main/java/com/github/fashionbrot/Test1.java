package com.github.fashionbrot;

import com.github.fashionbrot.entity.TestEntity;
import com.github.fashionbrot.vo.RespVo;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

public class Test1 {

    public static void main(String[] args) throws NoSuchFieldException {
        Field field = RespVo.class.getField("data");
        Type genericFieldType = field.getGenericType();
        if(genericFieldType instanceof ParameterizedType){
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            for(Type fieldArgType : fieldArgTypes){
                Class fieldArgClass = (Class) fieldArgType;
                System.out.println("fieldArgClass = " + fieldArgClass);
            }
        }

    }

    public RespVo<List<RespVo<TestEntity>>> test(){
        return RespVo.success();
    }

}
