package com.github.fashionbrot.entity;

import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.req.PageReq2;
import lombok.Data;

import java.util.List;

/**
 * @author fashionbrot
 */
@Data
public class Multi2Test extends PageReq2 {

    @ApiModelProperty("m1哦")
    private String m1;

    @ApiModelProperty("m3哦")
    private long m2;

    @ApiModelProperty("子类哦")
    private List<Test1> test1;

    @ApiModelProperty("子类2哦")
    private Test1[] test2;

}
