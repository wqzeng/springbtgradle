package com.wqzeng.test;

import com.alibaba.fastjson.JSONObject;
import com.wqzeng.springbtgradle.entity.Goods;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamTest {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Test
    public void testMap() {
        Integer now=5;
        List<Goods> goodsList = new ArrayList<>(10);
        for (int i = 0; i <10 ; i++) {
            Goods goods=new Goods();
            goods.setTime(i);
            goods.setName("test"+i);
            goodsList.add(goods);
        }
        for (int i = 0; i <10 ; i++) {
            Goods goods=new Goods();
            goods.setTime(i+1);
            goods.setName("test"+i);
            goodsList.add(goods);
        }

        Map<Integer, Goods> goodsMap = goodsList.stream().filter(goods -> goods.getTime() < now).collect(Collectors.toMap(goods -> goods.getTime(), g -> g, (key1, key2) -> key2));
        Map<Integer, Goods> goodsMap2 = goodsList.stream().filter(goods -> goods.getTime() >= now).collect(Collectors.toMap(goods -> goods.getTime(), g -> g, (key1, key2) -> key1));
        Map<Integer, List<Goods>> goodsMap3 = goodsList.stream().collect(Collectors.groupingBy(g->g.getTime()));
        Assert.assertEquals(goodsList.size(),20);
        logger.info(JSONObject.toJSONString(goodsMap));
        logger.info(JSONObject.toJSONString(goodsMap2));
        logger.info(JSONObject.toJSONString(goodsMap3));
    }

}
