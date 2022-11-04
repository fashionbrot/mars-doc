package com.github.fashionbrot.test;

import com.github.fashionbrot.doc.annotation.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class ControllerTest1 {

    @Controller
    @RequestMapping("test1")
    public class TestController{

        @ApiOperation("test1接口")
        @GetMapping("test1")
        @ResponseBody
        private String test1(String test){
            return "ok";
        }

    }


    public void test(){

    }



}
