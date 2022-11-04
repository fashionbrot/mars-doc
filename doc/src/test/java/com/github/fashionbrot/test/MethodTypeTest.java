package com.github.fashionbrot.test;

import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.util.MethodUtil;
import com.github.fashionbrot.doc.util.ResponseUtil;
import com.github.fashionbrot.doc.vo.MethodTypeVo;
import com.github.fashionbrot.doc.vo.ParameterVo;
import com.github.fashionbrot.entity.MultiTest;
import com.github.fashionbrot.req.TestReq;
import com.github.fashionbrot.vo.RespVo;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author fashionbrot
 */
public class MethodTypeTest {

    public class TestController{

        @ApiOperation("test10接口")
        @RequestMapping("test10")
        @ResponseBody
        private RespVo<MultiTest> test10(TestReq req){
            return RespVo.success();
        }

    }


    @Test
    public void test(){
        Method[] methods = ResponseTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test10")).findFirst().get();

        MethodTypeVo methodTypeVo = MethodUtil.getMethodTypeVo(method);
        System.out.println(methodTypeVo);

    }

}
