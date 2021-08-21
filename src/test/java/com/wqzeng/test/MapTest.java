package com.wqzeng.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.stream.Collectors;

public class MapTest {
    @Test
    public void test() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Integer key=0;
        Map<Integer,Integer> integerMap = new HashMap<>();
        Map<Integer,List<String>> integerListMap = new HashMap<>();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i <3 ; i++) {
            integerMap.put(key,integerMap.getOrDefault(key,0)+1);
            strings.add(i + "");
        }
        integerListMap.put(1,strings);
        System.out.println(integerMap.toString());
        System.out.println(integerListMap.toString());
        stopWatch.stop();
        Thread.sleep(10L);
        System.out.println("cost:"+stopWatch.getTotalTimeMillis());
        System.out.println("cost:"+stopWatch.getTotalTimeNanos());
        Assert.assertEquals(integerMap.size(),1);
        Assert.assertEquals(integerMap.get(key).intValue(),3);
    }
    @Test
    public void testString2Map(){
        Map<Long,List<String>> integerMap = new HashMap<>();
        List<String> s = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            s.add(i + "");
        }
        integerMap.put(1L,s);
        String intStringMap= JSONObject.toJSONString(integerMap);
        Map<Long,List<String>> integerMap2= new HashMap<>();
        integerMap2 = JSON.parseObject(intStringMap, HashMap.class);
        integerMap2 = JSONObject.parseObject(intStringMap, HashMap.class);
        System.out.println(integerMap2);
    }

    @Test
    public void testFor() {
        Map<Integer, List<String>> map = new HashMap<>();
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        map.computeIfAbsent(0, k -> Lists.newArrayList()).add("moke");


        Iterator<Map.Entry<Integer, List<String>>> entries = map.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry<Integer, List<String>> entry = entries.next();
            //可删除
//            entries.remove();
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        map.forEach((k,v)->{
            System.out.println("Key = " + k + ", Value = " + v);
        });
    }
}
