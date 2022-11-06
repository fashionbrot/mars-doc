package com.github.fashionbrot.test;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.util.ResponseUtil;
import com.github.fashionbrot.doc.vo.ParameterVo;
import com.github.fashionbrot.entity.Multi2Test;
import com.github.fashionbrot.entity.MultiTest;
import com.github.fashionbrot.entity.TestEntity;
import com.github.fashionbrot.req.TestReq;
import com.github.fashionbrot.vo.RespVo;
import com.github.fashionbrot.vo.RespVo2;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author fashionbrot
 */
public class ResponseTest {

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
        private RespVo<Multi2Test> test2(TestReq req){
            return RespVo.success();
        }


        @ApiOperation("test7接口")
        @RequestMapping("test7")
        @ResponseBody
        private RespVo2<Integer, RespVo2<String,List<TestEntity>>> test7(TestReq req){
            return RespVo2.success();
        }

        @ApiOperation("test8接口")
        @RequestMapping("test8")
        @ResponseBody
        private RespVo<TestEntity[]> test8( TestReq req){
            return RespVo.success();
        }


    }


    @Test
    public void test(){
        Method[] methods = ResponseTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> response = ResponseUtil.getResponse(method);

        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void test2(){
        Method[] methods = ResponseTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();
        List<ParameterVo> response = ResponseUtil.getResponse(method);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void test7(){
        Method[] methods = ResponseTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test7")).findFirst().get();
        List<ParameterVo> response = ResponseUtil.getResponse(method);
        System.out.println(JSON.toJSONString(response));
    }


    @Test
    public void test8(){
        Method[] methods = ResponseTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test8")).findFirst().get();
        List<ParameterVo> response = ResponseUtil.getResponse(method);
        System.out.println(JSON.toJSONString(response));
    }

}
