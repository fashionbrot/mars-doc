package com.github.fashionbrot.test;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.util.RequestUtil;
import com.github.fashionbrot.doc.util.ResponseUtil;
import com.github.fashionbrot.doc.vo.ParameterVo;
import com.github.fashionbrot.entity.CourseVO;
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
public class ResponseComplexTest {

    public class TestController{

        @ApiOperation("test1接口")
        @RequestMapping("test1")
        @ResponseBody
        private CourseVO test1( ){
            return new CourseVO();
        }

    }


    @Test
    public void test(){
        Method[] methods = ResponseComplexTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse2(method);

        System.out.println(JSON.toJSONString(request));
    }
}
