package com.wqzeng.springbtgradle.model.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 8种基本数据包装类
 */
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

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public Short getaShort() {
        return aShort;
    }

    public void setaShort(Short aShort) {
        this.aShort = aShort;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public void setaDouble(Double aDouble) {
        this.aDouble = aDouble;
    }

    public Character getaChar() {
        return aChar;
    }

    public void setaChar(Character aChar) {
        this.aChar = aChar;
    }

    public Byte getaByte() {
        return aByte;
    }

    public void setaByte(Byte aByte) {
        this.aByte = aByte;
    }

    public Float getaFloat() {
        return aFloat;
    }

    public void setaFloat(Float aFloat) {
        this.aFloat = aFloat;
    }

    public Long getaLong() {
        return aLong;
    }

    public void setaLong(Long aLong) {
        this.aLong = aLong;
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }
}
