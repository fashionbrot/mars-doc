package com.github.fashionbrot.doc.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParameterVo implements Serializable {
    private static final long serialVersionUID = -2654079955765557623L;

    /**
     * 字段名
     */
    private String name;
    /**
     * 描述
     */
    private String description;

    /**
     * 是否必填
     */
    private boolean required;

    /**
     * 类型
     */
    private String dataType;

    /**
     * 属性的示例值
     */
    private String example;

    /**
     * query or  body
     */
    private String in;

    private String ref;

    /**
     * 子参数
     */
    private List<ParameterVo> child;
}
