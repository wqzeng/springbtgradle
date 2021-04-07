package com.wqzeng.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SetTest {
    @Test
    public void test() {
        Set<Integer> setInt = new HashSet<>();
        setInt.add(1);
        setInt.add(2);
        setInt.add(233);
        setInt.add(243);
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(233L);
        Set<Integer> setInt2=new HashSet<>();
        setInt2.addAll(list.stream().map(Long::intValue).collect(Collectors.toSet()));

        setInt.retainAll(setInt2);
        Assert.assertEquals(setInt.size(),3);

    }
    public void testSetString() {
        Set<String> setString = new HashSet<>();
        setString.add("1");
        setString.add("2");
        setString.add("233");
        setString.add("243");
        setString.toArray();

    }
}
