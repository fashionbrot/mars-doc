package com.github.fashionbrot.test.response;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.util.ResponseUtil;
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
public class ResponseTest3 {


    public class ResponseCar{
        @ApiModelProperty("carName 汽车名")
        private String carName;

    }

    public class TestController{

        @ApiOperation("test1接口")
        @RequestMapping("test1")
        @ResponseBody
        private List<ResponseCar> test1( ){
            return null;
        }


        @ApiOperation("test2接口")
        @RequestMapping("test2")
        @ResponseBody
        private ResponseCar[] test2( ){
            return null;
        }
    }

    @Test
    public void testList(){
        Method[] methods = ResponseTest3.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse3(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"carName 汽车名\",\"example\":\"\",\"multiple\":\"\",\"name\":\"carName\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.response.ResponseTest3$ResponseCar\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"java.util.List\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }


    @Test
    public void testArray(){
        Method[] methods = ResponseTest3.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse3(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"carName 汽车名\",\"example\":\"\",\"multiple\":\"\",\"name\":\"carName\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.response.ResponseTest3$ResponseCar\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"com.github.fashionbrot.test.response.ResponseTest3$ResponseCar[]\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }

}
