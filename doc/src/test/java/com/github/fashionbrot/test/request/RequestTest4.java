package com.github.fashionbrot.test.request;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.util.RequestUtil;
import com.github.fashionbrot.doc.vo.ParameterVo;
import com.github.fashionbrot.entity.CourseVO;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class RequestTest4 {


    public class TestController{
        private void test1(CourseVO courseVO){

        }
    }

    @Test
    public void test1(){
        Method[] methods = RequestTest4.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = RequestUtil.getRequest(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "";
//        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }


}
