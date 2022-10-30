package com.github.fashionbrot.controller;

import com.github.fashionbrot.doc.annotation.Api;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.entity.TestEntity;
import com.github.fashionbrot.req.TestReq;
import com.github.fashionbrot.vo.RespVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Api("test接口类")
@Controller
@RequestMapping(value = {"/test","t1"})
public class TestController {


//    @ApiOperation("test1接口")
//    @GetMapping("test1")
//    @ResponseBody
//    private String test1(String test){
//        return "ok";
//    }
//
//
//    @ApiOperation("test2接口")
//    @GetMapping("test2")
//    @ResponseBody
//    private RespVo<TestEntity> test2(String test){
//        return RespVo.success();
//    }
//
    @ApiOperation("test3接口")
    @GetMapping("test3")
    @ResponseBody
    private RespVo<List<TestEntity>> test3(TestReq req){
        return RespVo.success();
    }

//    @ApiOperation("test4接口")
//    @RequestMapping("test4")
//    @ResponseBody
//    private RespVo<List<TestEntity>> test4(@RequestBody TestReq req){
//        return RespVo.success();
//    }


}
