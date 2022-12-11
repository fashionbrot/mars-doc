package com.github.fashionbrot.controller;


import com.github.fashionbrot.doc.annotation.Api;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.req.RequestReq1;
import com.github.fashionbrot.req.RequestReq2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Api(value = "requestTest接口",priority = 98)
@Controller
@RequestMapping("/request")
public class RequestController {

    @ApiOperation(value = "request1测试接口,名字能有多长呢",priority = 100)
    @ResponseBody
    @GetMapping("request1")
    public void request1(@ApiModelProperty("test1哦") String test1,@ApiModelProperty("test1哦") String test2){

    }

    @ApiOperation(value = "request2测试接口",priority = 80)
    @ResponseBody
    @GetMapping("request2")
    public void request1(RequestReq1 requestReq1){

    }


    @ApiOperation(value = "request3测试接口",priority = 70)
    @ResponseBody
    @PostMapping("request3")
    public void request3(@RequestBody RequestReq1 requestReq1){

    }

    @ApiOperation(value = "request4测试接口",priority = 60)
    @ResponseBody
    @GetMapping("request4")
    public void request4( RequestReq2 requestReq1){

    }


    @ApiOperation(value = "request5测试接口",priority = 50)
    @ResponseBody
    @PostMapping("request5")
    public void request5(@RequestBody RequestReq2 requestReq1){

    }


}
