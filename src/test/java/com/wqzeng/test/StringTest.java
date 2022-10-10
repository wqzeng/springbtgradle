package com.wqzeng.test;

import org.junit.Test;

import java.util.regex.Pattern;

public class StringTest {
    private static final Pattern TOKENS = Pattern.compile("(\\s+)|(AND)|(OR)|(=)|(\\()|(\\))|(\\w+)|\'([^\']+)\'");

    @Test
    public void splitTest() {
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
    public void indexTest() {
        String str="Gender";
        String name="gender";
        System.out.println(str.toLowerCase().indexOf(name));
    }

}
