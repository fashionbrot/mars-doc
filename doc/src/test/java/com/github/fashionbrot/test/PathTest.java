package com.github.fashionbrot.test;

import com.github.fashionbrot.doc.util.PathUtil;
import org.junit.Assert;
import org.junit.Test;

public class PathTest {


    @Test
    public void test1(){
        String s = PathUtil.formatPath("api", "test");
        System.out.println(s);
        Assert.assertEquals("/api/test",s);
    }



}
