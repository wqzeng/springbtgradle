package com.wqzeng.test;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class StringTest {
    private static final Pattern TOKENS = Pattern.compile("(\\s+)|(AND)|(OR)|(=)|(\\()|(\\))|(\\w+)|\'([^\']+)\'");

    @Test
    public void testSplit() {
        String str="Chr in (chrM) and Mutant Frequency>=5% and Mutant Reads>150";
        String[] array=str.split("and");
        for (int i = 0; i < array.length; i++) {
            String rule=array[i].trim();
            System.out.println(rule);
            String regex = "";
            Pattern pattern = Pattern.compile(regex);
            String[] parms=rule.split("/|in|>=|>");
            for (int j = 0; j <parms.length; j++) {
                System.out.println(parms[j]);
            }
        }
    }
    @Test
    public void testIndexOf() {
        String str="Gender";
        String name="gender";
        System.out.println(str.toLowerCase().indexOf(name));
    }
    @Test
    public void testFormat () {
        DecimalFormat formatter = new DecimalFormat("0.#E0");
        String str = "32";
        //判断str是否可以转换成BigDecimal
        boolean flag = str.matches("[+-]?(\\d+(\\.?\\d*)?|\\.\\d+)([Ee][+-]?\\d+)?");
        if (flag) {
            BigDecimal decimal=new BigDecimal(str);
//            System.out.println(decimal.setScale(2,BigDecimal.ROUND_HALF_UP));
            String de= decimal.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP).toString()+"%";
            System.out.println(de);
            System.out.printf(formatter.format(decimal.setScale(3,BigDecimal.ROUND_HALF_UP)));
        }
    }

}
