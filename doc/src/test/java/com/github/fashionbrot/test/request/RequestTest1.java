package com.github.fashionbrot.test.request;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiIgnore;
import com.github.fashionbrot.doc.util.RequestUtil;
import com.github.fashionbrot.doc.vo.ParameterVo;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author fashionbrot
 */
public class RequestTest1 {

    public class TestController{

        private void test1(String string,Integer integer, HashMap hashMap,@ApiIgnore Object object){

        }
    }

    @Test
    public void test1(){
        Method[] methods = RequestTest1.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = RequestUtil.getRequest(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"dataType\":\"java.lang.String\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"string\",\"required\":false},{\"dataType\":\"java.lang.Integer\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"integer\",\"required\":false},{\"dataType\":\"java.util.HashMap\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"hashMap\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }


    public class TestController2{

        private void test2(String[] string,List<Integer> integer){

        }
    }

    @Test
    public void test2(){
        Method[] methods = RequestTest1.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();

        List<ParameterVo> request = RequestUtil.getRequest(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"collection\":1,\"dataType\":\"java.lang.String\",\"description\":\"\",\"example\":\"\",\"isPrimitive\":1,\"multiple\":\"\",\"name\":\"string\",\"required\":false},{\"collection\":1,\"dataType\":\"java.lang.Integer\",\"description\":\"\",\"example\":\"\",\"isPrimitive\":1,\"multiple\":\"\",\"name\":\"integer\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }


}
