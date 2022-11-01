package com.github.fashionbrot.doc.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MethodTypeVo {

    private String methodId;

    private String typeClass;

    private String typeName;

    private List<MethodTypeVo> child;
}
