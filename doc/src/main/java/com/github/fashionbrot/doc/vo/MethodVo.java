package com.github.fashionbrot.doc.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MethodVo implements Serializable {
    private static final long serialVersionUID = 1731070425404707077L;


    /**
     * 接口描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String path;
    /**
     * 方法 "GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS" and "PATCH"
     */
    private String method;

    /**
     * 请求类型
     */
    private String contentType;

    /**
     * 优先级
     */
    private int priority;

    /**
     * 方法id
     */
    private String methodId;


}
