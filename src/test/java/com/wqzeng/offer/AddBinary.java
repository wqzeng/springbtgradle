package com.wqzeng.offer;

/**
 * 二进制加法
 */
public class AddBinary {
    public static void main(String[] args) {
        System.out.println(addBinary("10001","1001"));
    }

    public static String addBinary(String a, String b) {
        StringBuilder result = new StringBuilder();
        int i=a.length()-1;
        int j=b.length()-1;
        int carry=0;//进位
        while (i >= 0 || j >= 0) {
            int digitA = i >= 0 ? a.charAt(i) - '0' : 0;i--;
            int digitB = j >= 0 ? b.charAt(j) - '0' : 0;j--;
            int sum = digitA + digitB + carry;
            carry = sum >= 2 ? 1 : 0;
            sum = sum >= 2 ? sum - 2 : sum;
            result.append(sum);
        }
        if (carry == 1) {
            result.append(1);
        }
        return result.reverse().toString();
    }
}
