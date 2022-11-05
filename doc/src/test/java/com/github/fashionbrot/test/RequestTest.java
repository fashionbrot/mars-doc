package com.github.fashionbrot.test;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.util.RequestUtil;
import com.github.fashionbrot.doc.vo.ParameterVo;
import com.github.fashionbrot.entity.Multi2Test;
import com.github.fashionbrot.entity.MultiTest;
import com.github.fashionbrot.req.TestReq;
import com.github.fashionbrot.vo.RespVo;
import lombok.Data;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author fashionbrot
 */
public class RequestTest {

    public class TestController{

        @ApiOperation("test1接口")
        @RequestMapping("test1")
        @ResponseBody
        private RespVo<MultiTest> test1(TestReq req){
            return RespVo.success();
        }


        @ApiOperation("test2接口")
        @RequestMapping("test2")
        @ResponseBody
        private RespVo<Multi2Test> test2(@RequestBody Multi2Test req){
            return RespVo.success();
        }


    }


    @Test
    public void test(){
        Method[] methods = RequestTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = RequestUtil.getRequest(method);

        System.out.println(JSON.toJSONString(request));
    }

    @Test
    public void test2(){
        Method[] methods = RequestTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();

        List<ParameterVo> request = RequestUtil.getRequest(method);

        System.out.println(JSON.toJSONString(request));
    }



    @ApiModel("RequestReq1")
    @Data
    public class RequestReq1{

        @ApiModelProperty("test1哦")
        private String test1;

        @ApiModelProperty("requestReq2哦")
        private RequestReq2 requestReq2;
    }

    @ApiModel("RequestReq2")
    @Data
    public class RequestReq2 extends RequestReq3{

        @ApiModelProperty("test2哦")
        private String test2;
    }

    @ApiModel("RequestReq3")
    @Data
    public class RequestReq3{

        @ApiModelProperty("test3哦")
        private String test3;
    }

    @Controller
    public class TestController2{

        @ApiOperation("test3接口")
        @RequestMapping("test3")
        @ResponseBody
        private RespVo test3(RequestReq1 req){
            return RespVo.success();
        }

        @ApiOperation("test4接口")
        @RequestMapping("test4")
        @ResponseBody
        private RespVo test4(@RequestBody RequestReq1 req){
            return RespVo.success();
        }
    }

    @Test
    public void test3(){
        Method[] methods = RequestTest.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test3")).findFirst().get();

        List<ParameterVo> request = RequestUtil.getRequest(method);

        System.out.println(JSON.toJSONString(request));
    }

    @Test
    public void test4(){
        Method[] methods = RequestTest.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test4")).findFirst().get();

        List<ParameterVo> request = RequestUtil.getRequest(method);

        System.out.println(JSON.toJSONString(request));
    }


}
