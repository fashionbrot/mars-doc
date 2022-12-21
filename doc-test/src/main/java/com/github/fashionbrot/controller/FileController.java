package com.github.fashionbrot.controller;


import com.github.fashionbrot.doc.RespVo;
import com.github.fashionbrot.doc.annotation.Api;
import com.github.fashionbrot.doc.annotation.ApiIgnore;
import com.github.fashionbrot.doc.annotation.ApiImplicitParam;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.req.RequestReq2;
import com.github.fashionbrot.req.Upload2Req;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(value = "file接口类",priority = 90)
@Controller
public class FileController {

    @ApiImplicitParam(name ="uploadFile1",value = "文件",paramType = "query",dataType = "file",multiple = "multiple")
    @ApiOperation(value = "upload1接口",priority = 70)
    @PostMapping("upload1")
    @ResponseBody
    private RespVo upload1(@ApiIgnore @RequestPart("uploadFile1") List<MultipartFile> multipartFile, String test1, String test2){
        return RespVo.success();
    }

    @ApiImplicitParam(name ="uploadFile2",value = "文件",paramType = "query",dataType = "file",multiple = "multiple")
    @ApiOperation(value = "upload2接口",priority = 60)
    @PostMapping("upload2")
    @ResponseBody
    private RespVo upload2(@ApiIgnore @RequestParam("uploadFile2") MultipartFile[] multipartFile, Upload2Req req){
        return RespVo.success();
    }

    @ApiOperation(value = "upload3接口",priority = 50)
    @ResponseBody
    @PostMapping("upload3")
    public RespVo<Integer> upload3(@RequestBody RequestReq2 requestReq1){

        return RespVo.success(1);
    }

    @ApiOperation(value = "upload4接口",priority = 40)
    @ResponseBody
    @GetMapping("upload4")
    public RespVo<Integer> upload4( Upload2Req requestReq1){

        return RespVo.success(1);
    }

}
