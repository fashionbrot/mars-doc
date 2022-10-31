package com.github.fashionbrot.vo;


import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.enums.RespEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* 统一返回vo 类
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("RespVo统一返回类型")
public class RespVo2<C,M,T> {


    @ApiModelProperty("0成功,其他失败")
    private C code;
    @ApiModelProperty("成功失败消息")
    private M msg;
    @ApiModelProperty("返回的数据")
    private T data;


    public static <C,M,T>RespVo2 success(C code,M msg,T data){
        return new RespVo2(code,msg,data);
    }


    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(RespVo2.success("code","msg","data")) );
        System.out.println(JSON.toJSONString(RespVo2.success(1,"msg","data")) );
    }

}
