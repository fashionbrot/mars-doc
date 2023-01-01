package com.github.fashionbrot.test;

import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiIgnore;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.util.ObjectUtil;
import com.github.fashionbrot.doc.util.ParseUtil;
import com.github.fashionbrot.doc.util.RequestUtil;
import com.github.fashionbrot.doc.util.RequestUtil2;
import com.github.fashionbrot.doc.vo.ParameterVo;
import com.github.fashionbrot.entity.*;
import com.github.fashionbrot.req.TestReq;
import com.github.fashionbrot.vo.RespVo;
import lombok.Data;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author fashionbrot
 */
public class RequestComplexTest {


    @Data
    public class RequestTest extends RequestParent<RequestParent1>{
        private String t1;
    }
    @Data
    public class RequestTest2{
        private String t2;
    }
    @Data
    public class RequestParent<T>{
        private T  t;
    }
    @Data
    public class RequestParent1{
        private String t00;
    }


    public class RequestController{
        @ApiOperation("test1接口")
        @RequestMapping("test1")
        @ResponseBody
        private RespVo test1(RequestTest requestTest){
            return RespVo.success();
        }
    }

    @Test
    public void requestTest1(){
        Method[] methods = RequestComplexTest.RequestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        List<ParameterVo> request = RequestUtil.getRequest(method);
        System.out.println(JSON.toJSONString(request));
    }




    public class TestController{

        @ApiOperation("test2接口")
        @RequestMapping("test2")
        @ResponseBody
        private RespVo test2(CourseVO req){
            return RespVo.success();
        }

        @ApiOperation("test3接口")
        @RequestMapping("test3")
        @ResponseBody
        private RespVo test3( RequestReq3 requestReq3){
            return RespVo.success();
        }

    }


    @Test
    public void test2(){
        Method[] methods = RequestComplexTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();

        List<ParameterVo> request = RequestUtil.getRequest(method);

        System.out.println(JSON.toJSONString(request));
    }





    @Data
    public class RequestReq3  {

//        @ApiModelProperty("userList")
//        private List<RequestUser> userList;
//
//        @ApiModelProperty("userArray")
//        private RequestUser[] userArray;
//
//        private List<String> stringList;
//        private Integer[] integers;
//        @ApiModelProperty("r1")
//        private String r1;
//
//        private List<String> r2;
//        private String[] r3;
//        private Map<String,Object> r4;
//        private Object r5;
//        private RequestUser requestUser;

        private List<RequestUser> listList;
    }

    @Data
    public class RequestUser {
        @ApiModelProperty("username用户名")
        private String username;

    }

    @Data
        public class RequestCourse extends  RequestHouse{
        @ApiModelProperty("courseName 课程名")
        private String courseName;
    }

    @Data
    public class RequestHouse {
//        @ApiModelProperty("houseName 房子名")
//        private String houseName;
        private List<RequestCar> carList;
    }

    public class RequestCar{
        @ApiModelProperty("carName 汽车名")
        private String carName;


    }

    public class TestController3{
        @ApiOperation("test3接口")
        @RequestMapping("test3")
        @ResponseBody
        private RespVo test3(RequestReq3 requestReq3){
            return RespVo.success();
        }
    }


    @Test
    public void test3(){

        Method[] methods = RequestComplexTest.TestController3.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test3")).findFirst().get();

        List<ParameterVo> request = ParseUtil.getRequest3(method);
        System.out.println(JSON.toJSONString(request));
    }

}
