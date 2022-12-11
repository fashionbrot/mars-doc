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
public class LinkVo {

    private String methodId ;

    private List<ParameterVo> list;
}
