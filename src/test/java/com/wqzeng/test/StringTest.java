package com.wqzeng.test;

import org.junit.Test;

public class StringTest {

    @Test
    public void splitTest() {
        String str="100";
        String[] array=str.split("-");
        System.out.println(array.length);
    }

}
