package com.wqzeng.offer;

import org.junit.Assert;
import org.junit.Test;

public class ArrayTest {

    @Test
    public void testTwoSum() {
        int[] num=new int[]{1, 2, 4, 6, 10};
        int[] twoSum = twoSum(num, 8);
        Assert.assertEquals(1, twoSum[0]);
        Assert.assertEquals(3, twoSum[1]);
        twoSum = twoSum(num, 10);
        Assert.assertEquals(2, twoSum[0]);
        Assert.assertEquals(3, twoSum[1]);
    }
    /**
     * 指定数组num，求数组的2个元素之和等于target的下标
     * @param num  如:[1,2,4,6,10]
     * @param target 8
     * @return [1,3]
     */
    private int[] twoSum(int[] num, int target) {
        int i=0;
        int j=num.length-1;
        while (i < j && num[i] + num[j] != target) {
            if (num[i] + num[j] < target) {
                i++;
            }else{
                j--;
            }
        }
        return new int[]{i,j};
    }
}
