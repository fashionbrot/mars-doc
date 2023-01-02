package com.github.fashionbrot.test;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.util.ResponseUtilOld;
import com.github.fashionbrot.doc.vo.ParameterVo;
import com.github.fashionbrot.vo.PageVo2;
import lombok.Data;
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

    @Data
    public class ResponseVo {

//        @ApiModelProperty("userList")
//        private List<List<ResponseHouse>> userList;
//
//        @ApiModelProperty("userArray")
//        private ResponseUser[] userArray;
//
//        private List<String> stringList;
        @ApiModelProperty("integers 哦哦")
        private Integer[] integers;
//        @ApiModelProperty("r1")
//        private List<ResponseCar> r1;
//
//        private List<String> r2;
//        private String[] r3;
//        private Map<String,Object> r4;
//        private Object r5;
//        private ResponseUser requestUser;
//
//        private List<ResponseCar> listList;
//
//        private List<ResponseUser> responseUsers;
    }

    @Data
    public class ResponseUser extends ResponseCourse {
        @ApiModelProperty("username用户名")
        private String username;

    }

    @Data
    public class ResponseCourse extends ResponseHouse {
        @ApiModelProperty("courseName 课程名")
        private String courseName;
    }

    @Data
    public class ResponseHouse {
//                @ApiModelProperty("houseName 房子名")
//        private String houseName;
        private ResponseCar[] carList;
    }

    public class ResponseCar{
        @ApiModelProperty("carName 汽车名")
        private String carName;


    }

    public class TestController{

        @ApiOperation("test1接口")
        @RequestMapping("test1")
        @ResponseBody
        private PageVo2<ResponseCar> test1( ){
            return null;
        }

    }

    @Test
    public void test(){
        Method[] methods = ResponseComplexTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = ResponseUtilOld.getResponse3(method);

        System.out.println(JSON.toJSONString(request));
    }

}
