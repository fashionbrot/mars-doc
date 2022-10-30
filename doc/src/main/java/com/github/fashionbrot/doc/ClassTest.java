package com.github.fashionbrot.doc;

import com.alibaba.fastjson2.JSON;

public class ClassTest {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String str = "com.github.fashionbrot.vo.RespVo.class";
        Class<?> aClass = Class.forName(str);


        Object o = aClass.newInstance();
        System.out.println(JSON.toJSONString(o));
    }
}
