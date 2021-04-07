package com.wqzeng.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.BitSet;

public class BitSetTest {

    @Test
    public void tets() {
        int[] array = new int[]{1, 2, 3, 22, 0, 3};
        BitSet bitSet = new BitSet(7);
//        将数组内容组bitmap
        for (int i = 0; i < array.length; i++) {
            bitSet.set(array[i], true);
        }
        for (int i = 0; i < bitSet.size(); i++) {
            System.out.println(bitSet.get(i));
        }
        Assert.assertEquals(64, bitSet.size());
        System.out.printf("bitset:"+bitSet);
    }

    @Test
    public void testIf() {
        int i=10;
        if(i<11){
            System.out.println("i<11");
        }else if(i<12){
            System.out.println("i<12");
        }
    }
}
