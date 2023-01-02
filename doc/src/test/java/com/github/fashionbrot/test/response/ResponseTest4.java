package com.github.fashionbrot.test.response;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.util.ResponseUtil;
import com.github.fashionbrot.doc.util.ResponseUtilOld;
import com.github.fashionbrot.doc.vo.ParameterVo;
import com.github.fashionbrot.vo.RespVo;
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
public class ResponseTest4 {


    public class ResponseCar{
        @ApiModelProperty("carName 汽车名")
        private String carName;

    }

    public class TestController1{

        @ApiOperation("test1接口")
        @RequestMapping("test1")
        @ResponseBody
        private RespVo<List<Integer>> test1( ){
            return null;
        }


        @ApiOperation("test2接口")
        @RequestMapping("test2")
        @ResponseBody
        private RespVo<Integer[]> test2( ){
            return null;
        }
    }

    @Test
    public void testListInteger(){
        Method[] methods = ResponseTest4.TestController1.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"dataType\":\"int\",\"description\":\"0成功,其他失败\",\"example\":\"\",\"multiple\":\"\",\"name\":\"code\",\"required\":false},{\"dataType\":\"java.lang.String\",\"description\":\"成功失败消息\",\"example\":\"\",\"multiple\":\"\",\"name\":\"msg\",\"required\":false},{\"child\":[],\"collection\":1,\"dataType\":\"java.lang.Integer\",\"description\":\"返回的数据\",\"example\":\"\",\"isPrimitive\":1,\"multiple\":\"\",\"name\":\"data\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }


    @Test
    public void testArrayInteger(){
        Method[] methods = ResponseTest4.TestController1.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"dataType\":\"int\",\"description\":\"0成功,其他失败\",\"example\":\"\",\"multiple\":\"\",\"name\":\"code\",\"required\":false},{\"dataType\":\"java.lang.String\",\"description\":\"成功失败消息\",\"example\":\"\",\"multiple\":\"\",\"name\":\"msg\",\"required\":false},{\"child\":[],\"collection\":1,\"dataType\":\"java.lang.Integer\",\"description\":\"返回的数据\",\"example\":\"\",\"isPrimitive\":1,\"multiple\":\"\",\"name\":\"data\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }





    public class TestController2{

        @ApiOperation("test1接口")
        @RequestMapping("test1")
        @ResponseBody
        private RespVo<List<ResponseCar>> test1( ){
            return null;
        }


        @ApiOperation("test2接口")
        @RequestMapping("test2")
        @ResponseBody
        private RespVo<ResponseCar[]> test2( ){
            return null;
        }
    }

    @Test
    public void testListResponseCar(){
        Method[] methods = ResponseTest4.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"dataType\":\"int\",\"description\":\"0成功,其他失败\",\"example\":\"\",\"multiple\":\"\",\"name\":\"code\",\"required\":false},{\"dataType\":\"java.lang.String\",\"description\":\"成功失败消息\",\"example\":\"\",\"multiple\":\"\",\"name\":\"msg\",\"required\":false},{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"carName 汽车名\",\"example\":\"\",\"multiple\":\"\",\"name\":\"carName\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.response.ResponseTest4$ResponseCar\",\"description\":\"返回的数据\",\"example\":\"\",\"multiple\":\"\",\"name\":\"data\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }


    @Test
    public void testArrayResponseCar(){
        Method[] methods = ResponseTest4.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"dataType\":\"int\",\"description\":\"0成功,其他失败\",\"example\":\"\",\"multiple\":\"\",\"name\":\"code\",\"required\":false},{\"dataType\":\"java.lang.String\",\"description\":\"成功失败消息\",\"example\":\"\",\"multiple\":\"\",\"name\":\"msg\",\"required\":false},{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"carName 汽车名\",\"example\":\"\",\"multiple\":\"\",\"name\":\"carName\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.response.ResponseTest4$ResponseCar\",\"description\":\"返回的数据\",\"example\":\"\",\"multiple\":\"\",\"name\":\"data\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }

}
