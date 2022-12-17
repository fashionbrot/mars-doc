package com.github.fashionbrot.doc.controller;


import com.github.fashionbrot.doc.annotation.ApiIgnore;
import com.github.fashionbrot.doc.event.DocApplicationListener;
import com.github.fashionbrot.doc.vo.DocVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ApiIgnore
@Controller
public class MarsDocController {



    @ResponseBody
    @RequestMapping("/mars/api")
    public List<DocVo> marsApi(){
        return DocApplicationListener.getDocVoList();
    }

}
