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
public class RespVo<T> implements Serializable{
    private static final long serialVersionUID = -3655390020082644681L;

    public static final int SUCCESS = RespEnum.SUCCESS.getCode();
    public static final int FAILED = RespEnum.FAIL.getCode();
    public static final RespVo vo = RespVo.success(null);

    @ApiModelProperty("0成功,其他失败")
    private int code;
    @ApiModelProperty("成功失败消息")
    private String msg;
    @ApiModelProperty("返回的数据")
    private T data;


    public static RespVo<Object> fail(String msg){
        return RespVo.builder().code(FAILED).msg(msg).build();
    }

    public static RespVo fail(String msg, int code){
        return RespVo.builder().code(code).msg(msg).build();
    }

    public static<T> RespVo success(T data){
        return RespVo.builder().code(SUCCESS).msg("成功").data(data).build();
    }

    public static RespVo success(){
        return vo;
    }

    public static RespVo respCode(RespEnum respCode){
        return RespVo.builder().code(respCode.getCode()).msg(respCode.getMsg()).build();
    }

}
