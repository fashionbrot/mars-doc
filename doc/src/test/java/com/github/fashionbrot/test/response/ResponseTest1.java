package com.github.fashionbrot.test.response;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.util.ResponseUtil;
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
public class ResponseTest1 {

    public class TestController{

        private String test1( ){
            return null;
        }


        private HashMap testMap( ){
            return null;
        }

        private Object testObject( ){
            return null;
        }
    }

    @Test
    public void testString(){
        Method[] methods = ResponseTest1.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse3(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"dataType\":\"java.lang.String\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"java.lang.String\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }

    @Test
    public void testMap(){
        Method[] methods = ResponseTest1.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("testMap")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse3(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"dataType\":\"java.util.HashMap\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"java.util.HashMap\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }

    @Test
    public void testObject(){
        Method[] methods = ResponseTest1.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("testObject")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse3(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"dataType\":\"java.lang.Object\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"java.lang.Object\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }

}
