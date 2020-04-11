package com.wqzeng.test;

import org.junit.Test;

public class PrimitiveTypeTest {

    PrimitiveType primitiveType=new PrimitiveType();

    @Test
    public void printfDefaultValue() {
        primitiveType.setInteger(8);
        primitiveType.printfDefaultValue();
        primitiveType.printfMaxValue();
    }
}