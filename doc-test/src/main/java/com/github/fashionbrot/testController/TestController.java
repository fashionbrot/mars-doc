package com.github.fashionbrot.testController;

import com.github.fashionbrot.annotation.ApiMethodAnnotation;
import com.github.fashionbrot.doc.annotation.Api;
import com.github.fashionbrot.doc.annotation.ApiIgnore;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.entity.MultiTest;
import com.github.fashionbrot.entity.TestEntity;
import com.github.fashionbrot.req.TestReq;
import com.github.fashionbrot.vo.RespVo;
import com.github.fashionbrot.vo.RespVo2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@ApiIgnore
@Api(value = "test接口类",priority = 100)
@Controller
@RequestMapping(value = {"/test", "t1"})
public class TestController {

    @ApiMethodAnnotation
    @ApiOperation(value = "test1接口",priority = 100)
    @GetMapping("test1")
    @ResponseBody
    private String test1(String test){
        return "ok";
    }

    @ApiMethodAnnotation
    @ApiOperation(value = "test2接口",priority = 99)
    @GetMapping("test2")
    @ResponseBody
    private RespVo<TestEntity> test2(String test){
        return RespVo.success();
    }

    @ApiOperation(value = "test3接口",priority = 98)
    @GetMapping("test3")
    @ResponseBody
    private RespVo<List<TestEntity>> test3(TestReq req){
        return RespVo.success();
    }

    @ApiOperation(value = "test4接口",priority = 97)
    @RequestMapping("test4")
    @ResponseBody
    private RespVo<List<TestEntity>> test4(@RequestBody TestReq req){
        return RespVo.success();
    }

    @ApiOperation("test5接口")
    @RequestMapping("test5")
    @ResponseBody
    private RespVo2<Integer,List<TestEntity>> test5( TestReq req){
        return RespVo2.success();
    }

    @ApiOperation("test6接口")
    @RequestMapping("test6")
    @ResponseBody
    private RespVo2<Integer,RespVo2<String,TestEntity>> test6( TestReq req){
        return RespVo2.success();
    }


    @ApiOperation("test7接口")
    @RequestMapping("test7")
    @ResponseBody
    private RespVo2<Integer,RespVo2<String,List<TestEntity>>> test7( TestReq req){
        return RespVo2.success();
    }


    @ApiOperation("test8接口")
    @RequestMapping("test8")
    @ResponseBody
    private RespVo<TestEntity[]> test8( TestReq req){
        return RespVo.success();
    }

    @ApiOperation("test9接口")
    @RequestMapping("test9")
    @ResponseBody
    private RespVo2<Integer, RespVo2<String, TestEntity[]>> test9(TestReq req) {
        return RespVo2.success();
    }

    @ApiOperation("test10接口")
    @RequestMapping("test10")
    @ResponseBody
    private RespVo<MultiTest> test10(TestReq req){
        return RespVo.success();
    }

    @ApiOperation("test11接口")
    @RequestMapping("test11")
    @ResponseBody
    private RespVo test11(TestReq req){
        return RespVo.success();
    }

}
