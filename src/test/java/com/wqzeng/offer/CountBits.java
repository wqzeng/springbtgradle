package com.wqzeng.offer;

/**
 * 前n个数字二进制形式中1的个数
 */
public class CountBits {
    public static void main(String[] args) {
        int[] result = countBits(4);
        for (int i = 0; i <result.length ; i++) {
            System.out.println(result[i]);
        }
    }

    public static int[] countBits(int num) {
        int[] result = new int[num + 1];
        for (int i = 0; i < num; ++i) {
            int j=i;
            while (j != 0) {
                result[i]++;
                j = j & (j - 1);
            }
        }
        return result;
    }
}
