package com.github.fashionbrot.test.response;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.util.ResponseUtil;
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
        private RespVo<RespVo<PageVo<ResponseCar>>> test1( ){
            return null;
        }

    }

    //TODO 需要实现验证
    @Test
    public void test(){
        Method[] methods = ResponseTest9.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse3(method);
        System.out.println(JSON.toJSONString(request));
        final String finalResult = "";
//        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }


}
