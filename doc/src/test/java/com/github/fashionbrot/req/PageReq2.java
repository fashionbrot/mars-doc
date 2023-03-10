package com.github.fashionbrot.req;


import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("pageReq")
public class PageReq2<T,T2> extends PageReq3<String,String>{

    @ApiModelProperty("页码")
    private String pageNum;

    @ApiModelProperty("每页数量")
    private String pageSize;

    @ApiModelProperty("test哦")
    private T t1;

    @ApiModelProperty("test哦")
    private T2 t2;
}
