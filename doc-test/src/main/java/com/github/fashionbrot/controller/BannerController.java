package com.github.fashionbrot.controller;

import com.github.fashionbrot.doc.annotation.Api;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.entity.TestEntity;
import com.github.fashionbrot.req.TestReq;
import com.github.fashionbrot.vo.RespVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


@Api(value = "banner接口类",priority = 99)
@Controller
public class BannerController {


    @ApiOperation("test1接口")
    @GetMapping("test1")
    @ResponseBody
    private String test1(String test){
        return "ok";
    }


    @ApiOperation("test2接口")
    @GetMapping("test2")
    @ResponseBody
    private RespVo<TestEntity> test2(String test){
        return RespVo.success();
    }

    @ApiOperation("test3接口")
    @GetMapping("test3")
    @ResponseBody
    private RespVo<List<TestEntity>> test3(TestReq req){
        return RespVo.success();
    }

    @ApiOperation(value = "test4接口",priority = 1000)
    @PostMapping("test4")
    @ResponseBody
    private RespVo<List<TestEntity>> test4(@RequestBody TestReq req){
        return RespVo.success(Arrays.asList(TestEntity.builder()
                        .test1("test1")
                        .test2(2)
                        .test3((short) 3)
                        .test4(4L)
                        .test5(5D)
                        .test6(new BigDecimal("6"))
                        .test7(7F)
                        .test8(false)
                        .test9(Byte.parseByte("9"))
                .build()));
    }


}
