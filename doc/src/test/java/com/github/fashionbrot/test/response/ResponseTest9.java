package com.github.fashionbrot.test.response;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.util.ResponseUtil;
import com.github.fashionbrot.doc.util.ResponseUtilOld;
import com.github.fashionbrot.doc.vo.ParameterVo;
import com.github.fashionbrot.vo.PageVo;
import com.github.fashionbrot.vo.RespVo;
import lombok.Data;
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
public class ResponseTest9 {

    @Data
    public class ResponseCar{
        @ApiModelProperty("carName 汽车名")
        private String carName;

    }



    public class TestController{

        @ApiOperation("test1接口")
        @RequestMapping("test1")
        @ResponseBody
        private RespVo<PageVo<RespVo<PageVo<ResponseCar>>>> test1( ){
            return null;
        }

    }


    @Test
    public void test(){
        Method[] methods = ResponseTest9.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse(method);
        System.out.println(JSON.toJSONString(request));
        final String finalResult = "[{\"dataType\":\"int\",\"description\":\"0成功,其他失败\",\"example\":\"\",\"multiple\":\"\",\"name\":\"code\",\"required\":false},{\"dataType\":\"java.lang.String\",\"description\":\"成功失败消息\",\"example\":\"\",\"multiple\":\"\",\"name\":\"msg\",\"required\":false},{\"child\":[{\"child\":[{\"dataType\":\"int\",\"description\":\"0成功,其他失败\",\"example\":\"\",\"multiple\":\"\",\"name\":\"code\",\"required\":false},{\"dataType\":\"java.lang.String\",\"description\":\"成功失败消息\",\"example\":\"\",\"multiple\":\"\",\"name\":\"msg\",\"required\":false},{\"child\":[{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"carName 汽车名\",\"example\":\"\",\"multiple\":\"\",\"name\":\"carName\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.response.ResponseTest9$ResponseCar\",\"description\":\"数据\",\"example\":\"\",\"multiple\":\"\",\"name\":\"rows\",\"required\":false},{\"dataType\":\"long\",\"description\":\"总页数\",\"example\":\"\",\"multiple\":\"\",\"name\":\"total\",\"required\":false}],\"dataType\":\"com.github.fashionbrot.vo.PageVo\",\"description\":\"返回的数据\",\"example\":\"\",\"multiple\":\"\",\"name\":\"data\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.vo.RespVo\",\"description\":\"数据\",\"example\":\"\",\"multiple\":\"\",\"name\":\"rows\",\"required\":false},{\"dataType\":\"long\",\"description\":\"总页数\",\"example\":\"\",\"multiple\":\"\",\"name\":\"total\",\"required\":false}],\"dataType\":\"com.github.fashionbrot.vo.PageVo\",\"description\":\"返回的数据\",\"example\":\"\",\"multiple\":\"\",\"name\":\"data\",\"required\":false}]";

        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }







    @ApiModel("pageVo")
    public class PageVo3<T> {

        @ApiModelProperty("数据")
        private T[] rows;

        @ApiModelProperty("数据")
        private List<Integer> integers;

        @ApiModelProperty("总页数")
        private long total;

    }
    public class TestController2{

        @ApiOperation("test2接口")
        @RequestMapping("test2")
        @ResponseBody
        private PageVo3<Integer> test2( ){
            return null;
        }

    }


    @Test
    public void test2(){
        Method[] methods = ResponseTest9.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse(method);
        System.out.println(JSON.toJSONString(request));
        final String finalResult = "[{\"collection\":1,\"dataType\":\"java.lang.Integer\",\"description\":\"数据\",\"example\":\"\",\"isPrimitive\":1,\"multiple\":\"\",\"name\":\"rows\",\"required\":false},{\"child\":[],\"collection\":1,\"dataType\":\"java.lang.Integer\",\"description\":\"数据\",\"example\":\"\",\"isPrimitive\":1,\"multiple\":\"\",\"name\":\"integers\",\"required\":false},{\"dataType\":\"long\",\"description\":\"总页数\",\"example\":\"\",\"multiple\":\"\",\"name\":\"total\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }
}
