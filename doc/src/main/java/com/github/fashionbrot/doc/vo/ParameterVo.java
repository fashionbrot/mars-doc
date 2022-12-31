package com.github.fashionbrot.doc.vo;


import com.github.fashionbrot.doc.enums.ParamTypeEnum;
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
     * @see ParamTypeEnum
     */
    private String requestType;

    /**
     * 泛型对应的 class
     */
    private String ref;

    /**
     * 是否是集合类型  1是 0否
     */
    private Integer collection;

    /**
     * 是否是基本类型 1是 0否
     */
    private Integer isPrimitive;

    /**
     * 子参数
     */
    private List<ParameterVo> child;

    /**
     * file 类型使用 multiple=“multiple”上传多个文件
     */
    private String multiple;
}
