package com.wqzeng.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class MapTest {
    @Test
    public void test() {
        Integer key=0;
        Map<Integer,Integer> integerMap = new HashMap<>();
        for (int i = 0; i <3 ; i++) {
            integerMap.put(key,integerMap.getOrDefault(key,0)+1);
        }
        Assert.assertEquals(integerMap.size(),1);
        Assert.assertEquals(integerMap.get(key).intValue(),3);
    }
}
