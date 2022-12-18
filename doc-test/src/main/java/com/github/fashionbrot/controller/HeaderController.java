//package com.github.fashionbrot.controller;
//
//import com.github.fashionbrot.doc.annotation.Api;
//import com.github.fashionbrot.doc.annotation.ApiImplicitParam;
//import com.github.fashionbrot.doc.annotation.ApiModelProperty;
//import com.github.fashionbrot.doc.annotation.ApiOperation;
//import com.github.fashionbrot.doc.enums.ParamTypeEnum;
//import com.github.fashionbrot.req.RequestReq1;
//import com.github.fashionbrot.vo.RespVo;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//@Api(value = "header接口",priority = 1)
//@Controller
//@RequestMapping("/header")
//public class HeaderController {
//
//
//    @ApiImplicitParam(name = "token",value = "token header",paramType="header",dataType = "String")
//    @ApiOperation(value = "request1测试接口",priority = 100)
//    @ResponseBody
//    @GetMapping("request1")
//    public RespVo request1(@ApiModelProperty("test1哦") String test1, @ApiModelProperty("test1哦") String test2){
//
//        return RespVo.success();
//    }
//
//    @ApiImplicitParam(name = "token",value = "token header",paramType="header",dataType = "String")
//    @ApiOperation(value = "request2测试接口",priority = 80)
//    @ResponseBody
//    @GetMapping("request2")
//    public RespVo request1(RequestReq1 requestReq1){
//
//        return RespVo.success();
//    }
//
//    @ApiImplicitParam(name = "token",value = "token header",paramType="header",dataType = "String")
//    @ApiOperation(value = "request3测试接口",priority = 70)
//    @ResponseBody
//    @PostMapping("request3")
//    public RespVo request3(@RequestBody RequestReq1 requestReq1){
//
//        return RespVo.success();
//    }
//
//}
