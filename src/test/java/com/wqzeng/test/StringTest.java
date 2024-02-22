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
        String pattern = "0.#E00";
        String place="#";
        for(int i=1;i<4;i++){
            //替换pattern中的#为小数位数
            place=place + "#";
        }
        System.out.println(place);
        DecimalFormat formatter = new DecimalFormat(pattern.replace("#", place));

        String str = "32789893234.1";
        //判断str是否可以转换成BigDecimal
        boolean flag = str.matches("[+-]?(\\d+(\\.?\\d*)?|\\.\\d+)([Ee][+-]?\\d+)?");
        if (flag) {
            BigDecimal decimal=new BigDecimal(str);
            System.out.printf(formatter.format(decimal).replaceAll("E(\\d+)", "E+$1"));
        }
    }

}
