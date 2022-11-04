package com.github.fashionbrot.entity;

import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import lombok.Data;

/**
 * @author fashionbrot
 */
@Data
public class Test1 {

    @ApiModelProperty("t1哦")
    private String t1;

    @ApiModelProperty("t2哦")
    private int t2;

    @ApiModelProperty("t3哦")
    private long t3;
}
