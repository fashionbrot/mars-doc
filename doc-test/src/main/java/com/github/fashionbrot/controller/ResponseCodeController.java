package com.github.fashionbrot.controller;

import com.github.fashionbrot.doc.annotation.*;
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


@ApiImplicitParams({
        @ApiImplicitParam(name ="test",value = "测试header",paramType = "header",dataType = "String")

})
@ApiResponses({
        @ApiResponse(code = "0", message = "成功"),
        @ApiResponse(code = "1", message = "失败")
})
@Api(value = "responseCode接口",priority = 97)
@Controller
@RequestMapping("/responseCode")
public class ResponseCodeController {




    @ApiResponses({
            @ApiResponse(code = "2", message = "重新登录"),
            @ApiResponse(code = "3", message = "随意")
    })
    @ApiOperation(value = "responseCode1",priority = 100)
    @ResponseBody
    @GetMapping("responseCode1")
    public RespVo response1(@ApiModelProperty("test1哦") String test1,
                             @ApiModelProperty("test1哦") String test2){

        return RespVo.success();
    }


}
