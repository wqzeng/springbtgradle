package com.wqzeng.springbtgradle.pattern.adapter.voltage;

/**
 * 220v电压输出类
 */
public class Voltage220V {
    public int output220V() {
        int src=220;
        System.out.println("电压："+src+"伏");
        return src;
    }
}
