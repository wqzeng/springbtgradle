package com.wqzeng.test;

import com.google.common.collect.Lists;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ListTest {
    @Test
    public void testEmptyList() {
        List<String> strings= Collections.emptyList();
        for (String s:strings){
            System.out.println("jjj");
        }
        List<String> strings1 = new ArrayList<>();
        strings1.add("test");
        strings.addAll(strings1);
        Assert.assertTrue(strings.isEmpty());
    }

    @Test
    public void testConvert2Set() {
        List<String> strings= new ArrayList<>();
        strings.add("test");
        strings.add("test");
        strings.add("test1");
        Set<String> stringSet=strings.stream().collect(Collectors.toSet());
        Assert.assertTrue(stringSet.size()==2);
    }

}
