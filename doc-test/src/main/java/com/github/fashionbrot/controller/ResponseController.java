package com.github.fashionbrot.controller;

import com.github.fashionbrot.doc.annotation.Api;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.entity.TestEntity;
import com.github.fashionbrot.vo.PageVo;
import com.github.fashionbrot.vo.RespVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author fashionbrot
 */
@Api(value = "responseTest接口",priority = 97)
@Controller
@RequestMapping("/response")
public class ResponseController {





    @ApiOperation(value = "request1测试接口,名字能有多长呢",priority = 100)
    @ResponseBody
    @GetMapping("response1")
    public RespVo<PageVo<RespVo<PageVo<TestEntity>>>> response1(@ApiModelProperty("test1哦") String test1,
                                                                @ApiModelProperty("test1哦") String test2){

        return RespVo.success();
    }
}
