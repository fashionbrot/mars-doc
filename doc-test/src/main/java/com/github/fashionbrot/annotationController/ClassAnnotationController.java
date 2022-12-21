package com.github.fashionbrot.annotationController;


import com.github.fashionbrot.annotation.ApiClassAnnotation;
import com.github.fashionbrot.doc.RespVo;
import com.github.fashionbrot.doc.annotation.Api;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ApiClassAnnotation
@Api(value = "测试class注解生成接口文档")
@Controller
public class ClassAnnotationController {



    @ApiOperation(value = "class1接口",priority = 100)
    @ResponseBody
    @GetMapping("class1")
    public RespVo class1(@ApiModelProperty("test1哦") String test1, @ApiModelProperty("test1哦") String test2){

        return RespVo.success();
    }

}
