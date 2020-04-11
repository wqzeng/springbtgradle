package com.wqzeng.test;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 8种基本数据包装类
 */
@Data
public class PrimitiveType {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 32位有符号整数
     */
    Integer integer;
    /**
     * 8位
     */
    Short aShort;
    /**
     * 双精度64位浮点数
     */
    Double aDouble;
    /**
     * 16位字符
     */
    Character aChar;
    /**
     * -128到127的8位整数
     */
    Byte aByte;
    /**
     * 双精度32位浮点数
     */
    Float aFloat;
    /**
     * 64位有符号整数
     */
    Long aLong;
    Boolean aBoolean;

    public void printfDefaultValue() {
        logger.info("default value-> Integer:{}, Short:{}", integer, aShort);
    }

    public void printfMaxValue() {
        logger.info("Interge maxValue:{}",Integer.MAX_VALUE);
    }

    public void add2Integer(Integer i){
        integer=integer+i;
        logger.info("interge={}",integer);
    }
}
