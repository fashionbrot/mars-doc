package com.github.fashionbrot.doc.controller;


import com.github.fashionbrot.doc.annotation.ApiIgnore;
import com.github.fashionbrot.doc.vo.DocVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ApiIgnore
@Controller
public class MarsDocController {

    public static DocVo docVo = null;


    @ResponseBody
    @RequestMapping("/mars/api")
    public DocVo marsApi(){

        return docVo;
    }

}
