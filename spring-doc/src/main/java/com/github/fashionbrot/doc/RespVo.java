package com.github.fashionbrot.doc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespVo<T> implements Serializable {
    private static final long serialVersionUID = -3655390020082644681L;

    public static final int SUCCESS = 0;
    public static final int FAILED = 1;
    public static final RespVo vo = RespVo.success(null);

    private int code;
    private String msg;
    private T data;


    public static RespVo fail(String msg){
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

}

