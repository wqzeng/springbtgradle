package com.wqzeng.springbtgradle.pattern.adapter.voltage;

/**
 * 组合原则，适配器类
 */
public class VoltageAdapter2 implements Voltage5V {
//    关联关系-聚合
    private Voltage220V voltage220V;

    public VoltageAdapter2(Voltage220V voltage220V) {
        this.voltage220V = voltage220V;
    }

    @Override
    public int output5V() {
//        获取到源电压
        int srcV=voltage220V.output220V();
//        降压适配转换
        int dstV=srcV/44;
        return dstV;
    }
}
