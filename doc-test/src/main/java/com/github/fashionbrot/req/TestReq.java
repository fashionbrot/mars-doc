package com.github.fashionbrot.req;

import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("测试表Entity")
public class TestReq {


    @ApiModelProperty("测试参数1")
    private String test1;


    @ApiModelProperty("test2-list")
    private List<Test2Req> test2;

}
