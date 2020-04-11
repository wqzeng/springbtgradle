package com.wqzeng.test;

import org.junit.Assert;
import org.junit.Test;

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
}