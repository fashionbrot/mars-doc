package com.github.fashionbrot.test.request;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.util.RequestUtil;
import com.github.fashionbrot.doc.vo.ParameterVo;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author fashionbrot
 */
public class RequestTest3 {

    @Data
    public class TestUser1 extends TestUser3{
        @ApiModelProperty("参数1")
        private String t1;

        private TestUser2 testUser2;

        private List<TestUser2> testUser2List;

        private TestUser2[] testUser2s;
    }

    @Data
    public class TestUser2{
        @ApiModelProperty("参数2")
        private String t2;

        private List<TestUser3> testUser3List;

        private TestUser3[] testUser3s;
    }
    @Data
    public class TestUser3{
        @ApiModelProperty("参数3")
        private String t3;
    }


    public class TestController{
        private void test1(TestUser1 testUser1){

        }
    }

    @Test
    public void test1(){
        Method[] methods = RequestTest3.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = RequestUtil.getRequest(method);
        System.out.println(JSON.toJSONString(request));
        String finalResult = "[{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"参数3\",\"example\":\"\",\"multiple\":\"\",\"name\":\"t3\",\"requestType\":\"QUERY\",\"required\":false},{\"dataType\":\"java.lang.String\",\"description\":\"参数1\",\"example\":\"\",\"multiple\":\"\",\"name\":\"t1\",\"requestType\":\"QUERY\",\"required\":false},{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"参数2\",\"example\":\"\",\"multiple\":\"\",\"name\":\"t2\",\"requestType\":\"QUERY\",\"required\":false},{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"参数3\",\"example\":\"\",\"multiple\":\"\",\"name\":\"t3\",\"requestType\":\"QUERY\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.request.RequestTest3$TestUser3\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"testUser3List\",\"requestType\":\"QUERY\",\"required\":false},{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"参数3\",\"example\":\"\",\"multiple\":\"\",\"name\":\"t3\",\"requestType\":\"QUERY\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.request.RequestTest3$TestUser3\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"testUser3s\",\"requestType\":\"QUERY\",\"required\":false}],\"dataType\":\"com.github.fashionbrot.test.request.RequestTest3$TestUser2\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"testUser2\",\"requestType\":\"QUERY\",\"required\":false},{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"参数2\",\"example\":\"\",\"multiple\":\"\",\"name\":\"t2\",\"requestType\":\"QUERY\",\"required\":false},{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"参数3\",\"example\":\"\",\"multiple\":\"\",\"name\":\"t3\",\"requestType\":\"QUERY\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.request.RequestTest3$TestUser3\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"testUser3List\",\"requestType\":\"QUERY\",\"required\":false},{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"参数3\",\"example\":\"\",\"multiple\":\"\",\"name\":\"t3\",\"requestType\":\"QUERY\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.request.RequestTest3$TestUser3\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"testUser3s\",\"requestType\":\"QUERY\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.request.RequestTest3$TestUser2\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"testUser2List\",\"requestType\":\"QUERY\",\"required\":false},{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"参数2\",\"example\":\"\",\"multiple\":\"\",\"name\":\"t2\",\"requestType\":\"QUERY\",\"required\":false},{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"参数3\",\"example\":\"\",\"multiple\":\"\",\"name\":\"t3\",\"requestType\":\"QUERY\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.request.RequestTest3$TestUser3\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"testUser3List\",\"requestType\":\"QUERY\",\"required\":false},{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"参数3\",\"example\":\"\",\"multiple\":\"\",\"name\":\"t3\",\"requestType\":\"QUERY\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.request.RequestTest3$TestUser3\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"testUser3s\",\"requestType\":\"QUERY\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.request.RequestTest3$TestUser2\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"testUser2s\",\"requestType\":\"QUERY\",\"required\":false}],\"dataType\":\"com.github.fashionbrot.test.request.RequestTest3$TestUser1\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"testUser1\",\"requestType\":\"QUERY\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }


}
