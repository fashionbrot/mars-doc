package com.github.fashionbrot.test.request;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.util.RequestUtil;
import com.github.fashionbrot.doc.vo.ParameterVo;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author fashionbrot
 */
public class RequestTest2 {

    @Data
    public class TestUser1{
        @ApiModelProperty("参数1")
        private String t1;
    }

    public class TestController{
        private void test1(@RequestBody TestUser1 testUser1){

        }
    }

    @Test
    public void test1(){
        Method[] methods = RequestTest2.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = RequestUtil.getRequest(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"参数1\",\"example\":\"\",\"multiple\":\"\",\"name\":\"t1\",\"requestType\":\"BODY\",\"required\":false}],\"dataType\":\"com.github.fashionbrot.test.request.RequestTest2$TestUser1\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"testUser1\",\"requestType\":\"BODY\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }


    @Data
    public class TestUser{
        @ApiModelProperty("参数1")
        private String t1;
    }
    public class TestController2{
        private void test2(@RequestBody List<TestUser> testUserList, TestUser[] testUsers){
        }
    }

    @Test
    public void test2(){
        Method[] methods = RequestTest2.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();

        List<ParameterVo> request = RequestUtil.getRequest(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"参数1\",\"example\":\"\",\"multiple\":\"\",\"name\":\"t1\",\"requestType\":\"BODY\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.request.RequestTest2$TestUser\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"testUserList\",\"requestType\":\"BODY\",\"required\":false},{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"参数1\",\"example\":\"\",\"multiple\":\"\",\"name\":\"t1\",\"requestType\":\"QUERY\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.request.RequestTest2$TestUser\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"testUsers\",\"requestType\":\"QUERY\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }


}
