package com.wqzeng.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StreamTest {
    @Test
    public void testMap() {
        Integer now=5;
        List<Goods> goodsList = new ArrayList<>(10);
        for (int i = 0; i <10 ; i++) {
            Goods goods=new Goods();
            goods.setTime(i);
            goodsList.add(goods);
        }

        Assert.assertEquals(goodsList.size(),10);
    }

}
