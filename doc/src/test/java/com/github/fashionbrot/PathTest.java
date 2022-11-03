package com.github.fashionbrot;

import com.github.fashionbrot.doc.util.PathUtil;
import org.junit.Assert;
import org.junit.Test;

public class PathTest {


    @Test
    private void test1(){
        String s = PathUtil.formatPath("api", "test");
        Assert.assertEquals("/api/test",s);

    }



}
