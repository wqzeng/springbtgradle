package com.wqzeng.test;

import com.wqzeng.springbtgradle.bean.PrimitiveType;
import org.junit.Assert;
import org.junit.Test;

import java.text.MessageFormat;

public class PrimitiveTypeTest {

    PrimitiveType primitiveType=new PrimitiveType();

    @Test
    public void printfDefaultValue() {
        primitiveType.setInteger(8);
        Assert.assertNotNull("i",primitiveType.getInteger());
        primitiveType.printfDefaultValue();
        primitiveType.printfMaxValue();
        primitiveType.add2Integer(2);
        Assert.assertEquals("integer add is fault",10,primitiveType.getInteger().intValue());
    }

    @Test
    public void printfMessageFormat() {
        long spu=23456789000998L;
        String s= MessageFormat.format("spu_{0}",spu+"");
        String s2= String.format("spu_%s",spu);
        Assert.assertEquals("spu_23456789000998",s);
        Assert.assertEquals("spu_23456789000998",s2);
    }
}