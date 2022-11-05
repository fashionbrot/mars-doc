package com.github.fashionbrot.req;


import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("pageReq")
public class PageReq2<T> {

    @ApiModelProperty("页码")
    private String pageNum;

    @ApiModelProperty("每页数量")
    private String pageSize;

    @ApiModelProperty("test哦")
    private T test;
}
