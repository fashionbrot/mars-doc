package com.github.fashionbrot.test.response;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.util.ResponseUtil;
import com.github.fashionbrot.doc.util.ResponseUtilOld;
import com.github.fashionbrot.doc.vo.ParameterVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author fashionbrot
 */
public class ResponseTest2 {

    public class TestController{

        @ApiOperation("test1接口")
        @RequestMapping("test1")
        @ResponseBody
        private List<String> test1( ){
            return null;
        }


        @ApiOperation("test2接口")
        @RequestMapping("test2")
        @ResponseBody
        private String[] test2( ){
            return null;
        }
    }

    @Test
    public void testList(){
        Method[] methods = ResponseTest2.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"collection\":1,\"dataType\":\"java.lang.String\",\"description\":\"\",\"example\":\"\",\"isPrimitive\":1,\"multiple\":\"\",\"name\":\"java.util.List\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }





    public class TestController2{
        @ApiOperation("test2接口")
        @RequestMapping("test2")
        @ResponseBody
        private String[] test2( ){
            return null;
        }
    }
    @Test
    public void testArray(){
        Method[] methods = ResponseTest2.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"collection\":1,\"dataType\":\"java.lang.String\",\"description\":\"\",\"example\":\"\",\"isPrimitive\":1,\"multiple\":\"\",\"name\":\"java.lang.String\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }

}
