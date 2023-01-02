package com.github.fashionbrot.vo;


import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@ApiModel("pageVo")
public class PageVo2<T> {

    @ApiModelProperty("数据")
    private List<T> rows;

    @ApiModelProperty("数据")
    private List<Integer> integers;

//    @ApiModelProperty("总页数")
//    private long total;

}
