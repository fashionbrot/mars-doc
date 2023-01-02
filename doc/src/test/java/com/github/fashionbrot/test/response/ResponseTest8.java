package com.github.fashionbrot.test.response;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.util.ResponseUtil;
import com.github.fashionbrot.doc.util.ResponseUtilOld;
import com.github.fashionbrot.doc.vo.ParameterVo;
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
public class ResponseTest8 {


    @Data
    @ApiModel("pageVo")
    public class PageVo2<T2,T1> {

        @ApiModelProperty("数据")
        private List<T1> rows;

        @ApiModelProperty("数据")
        private List<Integer> integers;

        private T1[] rows2;

        private Integer[] integers2;

        @ApiModelProperty("总页数")
        private T2 total;

    }
    public class ResponseCar{
        @ApiModelProperty("carName 汽车名")
        private String carName;

    }



    public class TestController{

        @ApiOperation("test1接口")
        @RequestMapping("test1")
        @ResponseBody
        private PageVo2<List<ResponseCar>,ResponseCar> test1( ){
            return null;
        }

    }

    @Test
    public void test(){
        Method[] methods = ResponseTest8.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = ResponseUtil.getResponse(method);
        System.out.println(JSON.toJSONString(request));
        final String finalResult = "[{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"carName 汽车名\",\"example\":\"\",\"multiple\":\"\",\"name\":\"carName\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.response.ResponseTest8$ResponseCar\",\"description\":\"数据\",\"example\":\"\",\"multiple\":\"\",\"name\":\"rows\",\"required\":false},{\"child\":[],\"collection\":1,\"dataType\":\"java.lang.Integer\",\"description\":\"数据\",\"example\":\"\",\"isPrimitive\":1,\"multiple\":\"\",\"name\":\"integers\",\"required\":false},{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"carName 汽车名\",\"example\":\"\",\"multiple\":\"\",\"name\":\"carName\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.response.ResponseTest8$ResponseCar\",\"description\":\"\",\"example\":\"\",\"multiple\":\"\",\"name\":\"rows2\",\"required\":false},{\"collection\":1,\"dataType\":\"java.lang.Integer\",\"description\":\"\",\"example\":\"\",\"isPrimitive\":1,\"multiple\":\"\",\"name\":\"integers2\",\"required\":false},{\"child\":[{\"dataType\":\"java.lang.String\",\"description\":\"carName 汽车名\",\"example\":\"\",\"multiple\":\"\",\"name\":\"carName\",\"required\":false}],\"collection\":1,\"dataType\":\"com.github.fashionbrot.test.response.ResponseTest8$ResponseCar\",\"description\":\"总页数\",\"example\":\"\",\"multiple\":\"\",\"name\":\"total\",\"required\":false}]";
        Assert.assertEquals(finalResult,JSON.toJSONString(request));
    }

}
