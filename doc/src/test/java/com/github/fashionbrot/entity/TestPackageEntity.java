package com.github.fashionbrot.entity;

import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("测试表Entity")
public class TestPackageEntity {



    @ApiModelProperty("测试参数2")
    private int test2;

    @ApiModelProperty("测试参数3")
    private short test3;

    @ApiModelProperty("测试参数4")
    private long test4;

    @ApiModelProperty("测试参数5")
    private double test5;


    @ApiModelProperty("测试参数7")
    private float test7;

    @ApiModelProperty("测试参数8")
    private boolean test8;

    @ApiModelProperty("测试参数9")
    private byte test9;

}
