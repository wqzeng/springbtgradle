package com.wqzeng.springbtgradle.constant.enums;

import org.junit.Assert;
import org.junit.Test;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

import static org.junit.Assert.*;

public class CalculatorEnumTest {

    @Test
    public void execute() {
        Double result=CalculatorEnum.ADDITION.execute(3.0,4.0);
        Assert.assertEquals(7.0,result.doubleValue(),2.0);
    }
    @Test
    public void testEnumSet(){
        EnumSet<CalculatorEnum> calculatorEnums=EnumSet.of(CalculatorEnum.ADDITION,CalculatorEnum.SUBTRACTION);
        Map<CalculatorEnum,Integer> map = new EnumMap<>(CalculatorEnum.class);

    }
}