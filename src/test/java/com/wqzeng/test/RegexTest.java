package com.wqzeng.test;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    @Test
    public void mobileTest() {
        String regex="1[3456789]\\d{9}";
        String context="2195100187973213510018989";
//        String context="1110018797";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(context);
        while (matcher.find()) {
            String group = matcher.group(0);
            System.out.println(group);
        }
    }
}
