package com.github.fashionbrot.test;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.util.RequestUtil;
import com.github.fashionbrot.doc.vo.ParameterVo;
import com.github.fashionbrot.entity.Multi2Test;
import com.github.fashionbrot.entity.MultiTest;
import com.github.fashionbrot.req.TestReq;
import com.github.fashionbrot.vo.RespVo;
import org.junit.Test;
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
}
