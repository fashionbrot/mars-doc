package com.github.fashionbrot.test;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiIgnore;
import com.github.fashionbrot.doc.annotation.ApiResponse;
import com.github.fashionbrot.doc.annotation.ApiResponses;
import com.github.fashionbrot.doc.util.ResponseCodeUtil;
import com.github.fashionbrot.doc.vo.ResponseCodeVo;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author fashionbrot
 */
public class ResponseCodeTest {


    @ApiResponses({
            @ApiResponse(code = "1000", message = "非HTTP状态码，返回值JSON code字段值，描述：成功"),
            @ApiResponse(code = "401", message = "非HTTP状态码，返回值JSON code字段值，描述：无token")
    })
    public class TestController{

        @ApiResponse(code = "1", message = "失败")
        @ApiResponses({
                @ApiResponse(code = "0", message = "成功")
        })
        private void test1(String string, Integer integer, HashMap hashMap, @ApiIgnore Object object){

        }
    }


    @Test
    public void test(){
        Method[] methods = ResponseCodeTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ResponseCodeVo> responseCodeList = ResponseCodeUtil.getResponseCodeList(method);
        System.out.println(JSON.toJSONString(responseCodeList));
        final String result="[{\"code\":\"0\",\"message\":\"成功\"},{\"code\":\"1\",\"message\":\"失败\"},{\"code\":\"1000\",\"message\":\"非HTTP状态码，返回值JSON code字段值，描述：成功\"},{\"code\":\"401\",\"message\":\"非HTTP状态码，返回值JSON code字段值，描述：无token\"}]";
        Assert.assertEquals(result,JSON.toJSONString(responseCodeList));
    }


}
