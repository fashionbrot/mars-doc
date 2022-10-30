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
public class ClassVo implements Serializable {
    private static final long serialVersionUID = -242836563047724263L;

    private String classId;
    /**
     * 类说明
     */
    private String description;

    /**
     * 优先级
     */
    private int priority;


    private List<MethodVo> methodList;



}
