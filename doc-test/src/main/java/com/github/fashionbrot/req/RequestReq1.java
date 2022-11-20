package com.github.fashionbrot.req;


import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("RequestReq1 哦哦")
public class RequestReq1 {

    @ApiModelProperty("test1哦")
    private String test1;

    @ApiModelProperty("test2哦")
    private String test2;
}
