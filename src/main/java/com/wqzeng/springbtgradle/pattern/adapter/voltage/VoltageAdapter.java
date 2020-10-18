package com.wqzeng.springbtgradle.pattern.adapter.voltage;

/**
 * 适配器类
 */
public class VoltageAdapter extends Voltage220V implements Voltage5V {
    @Override
    public int output5V() {
//        获取到源电压
        int srcV=output220V();
//        降压适配转换
        int dstV=srcV/44;
        return dstV;
    }
}
