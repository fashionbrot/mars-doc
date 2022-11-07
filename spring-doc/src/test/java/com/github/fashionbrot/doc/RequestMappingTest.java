package com.github.fashionbrot.doc;

import com.github.fashionbrot.doc.util.RequestMappingUtil;
import com.github.fashionbrot.doc.vo.MethodVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fashionbrot
 */
public class RequestMappingTest {

    @Controller
    @RequestMapping(value = {"test1","test2"})
    public class TestController{

        @RequestMapping("/test1")
        @ResponseBody
        public String test1(){
            return "";
        }

        @GetMapping("/test2")
        @ResponseBody
        public String test2(){
            return "";
        }

        @RequestMapping(value = {"test3"},method = {RequestMethod.GET,RequestMethod.POST})
        @ResponseBody
        public String test3(){
            return "";
        }


    }

    Method[] methods = RequestMappingTest.TestController.class.getDeclaredMethods();

    @Test
    public void test1(){

        List<MethodVo> requestMapping = RequestMappingUtil.getRequestMapping(Arrays.stream(methods).filter(m-> m.getName().equals("test1")).findFirst().get());
        String str  = requestMapping.stream().sorted(Comparator.comparing(MethodVo::getMethod)).map(m -> m.getMethod()+m.getPath()).collect(Collectors.joining());
        System.out.println(str);
        String result ="DELETE/test1/test1DELETE/test2/test1GET/test1/test1GET/test2/test1HEAD/test1/test1HEAD/test2/test1OPTIONS/test1/test1OPTIONS/test2/test1PATCH/test1/test1PATCH/test2/test1POST/test1/test1POST/test2/test1PUT/test1/test1PUT/test2/test1TRACE/test1/test1TRACE/test2/test1";
        System.out.println(result);
        Assert.assertEquals(result,str);
    }

    @Test
    public void test2(){
        List<MethodVo> requestMapping = RequestMappingUtil.getRequestMapping(Arrays.stream(methods).filter(m-> m.getName().equals("test2")).findFirst().get());
        String str  = requestMapping.stream().sorted(Comparator.comparing(MethodVo::getMethod)).map(m -> m.getMethod()+m.getPath()).collect(Collectors.joining());
        System.out.println(str);
        String result ="GET/test1/test2GET/test2/test2";
        System.out.println(result);
        Assert.assertEquals(result,str);
    }

    @Test
    public void test3(){
        List<MethodVo> requestMapping = RequestMappingUtil.getRequestMapping(Arrays.stream(methods).filter(m-> m.getName().equals("test3")).findFirst().get());
        String str  = requestMapping.stream().sorted(Comparator.comparing(MethodVo::getMethod)).map(m -> m.getMethod()+m.getPath()).collect(Collectors.joining());
        System.out.println(str);
        String result ="GET/test1/test3GET/test2/test3POST/test1/test3POST/test2/test3";
        System.out.println(result);
        Assert.assertEquals(result,str);
    }
}
