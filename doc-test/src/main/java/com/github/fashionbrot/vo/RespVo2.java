package com.github.fashionbrot.vo;


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
public class RespVo2<C,T> implements Serializable{
    private static final long serialVersionUID = -3655390020082644681L;

    public static final int SUCCESS = RespEnum.SUCCESS.getCode();
    public static final int FAILED = RespEnum.FAIL.getCode();
    public static final RespVo2 vo = RespVo2.success(null);

    @ApiModelProperty("0成功,其他失败")
    private C code;
    @ApiModelProperty("成功失败消息")
    private String msg;
    @ApiModelProperty("返回的数据")
    private T data;


    public static RespVo2 fail(String msg){
        return RespVo2.builder().code(FAILED).msg(msg).build();
    }

    public static RespVo2 fail(String msg, int code){
        return RespVo2.builder().code(code).msg(msg).build();
    }

    public static<T> RespVo2 success(T data){
        return RespVo2.builder().code(SUCCESS).msg("成功").data(data).build();
    }

    public static RespVo2 success(){
        return vo;
    }

    public static RespVo2 respCode(RespEnum respCode){
        return RespVo2.builder().code(respCode.getCode()).msg(respCode.getMsg()).build();
    }

}
