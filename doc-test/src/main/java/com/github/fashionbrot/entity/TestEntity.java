package com.github.fashionbrot.entity;

import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("测试表Entity")
public class TestEntity {


    @ApiModelProperty("测试参数1")
    private String test1;

    @ApiModelProperty("测试参数2")
    private Integer test2;

    @ApiModelProperty("测试参数3")
    private Short test3;

    @ApiModelProperty("测试参数4")
    private Long test4;

    @ApiModelProperty("测试参数5")
    private Double test5;

    @ApiModelProperty("测试参数6")
    private BigDecimal test6;

    @ApiModelProperty("测试参数7")
    private Float test7;

    @ApiModelProperty("测试参数8")
    private Boolean test8;

    @ApiModelProperty("测试参数9")
    private Byte test9;



}
