package com.github.fashionbrot.req;


import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("RequestReq2 哦哦")
public class RequestReq2 {

    @ApiModelProperty("test1哦")
    private String test1;

    @ApiModelProperty("requestReq1哦")
    private RequestReq1 requestReq1;

    @ApiModelProperty("requestReq1List哦")
    private List<RequestReq1> requestReq1List;

}
