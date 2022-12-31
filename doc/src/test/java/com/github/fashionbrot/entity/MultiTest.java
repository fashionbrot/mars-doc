package com.github.fashionbrot.entity;

import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import lombok.Data;

/**
 * @author fashionbrot
 */
@Data
public class MultiTest {

    @ApiModelProperty("m1哦")
    private String m1;

    @ApiModelProperty("m3哦")
    private long m2;

    @ApiModelProperty("子类哦")
    private Test1 test1;


}
