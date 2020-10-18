package com.wqzeng.springbtgradle.pattern.adapter.voltage;

public class Client {
    public static void main(String[] args) throws Exception{
        System.out.println("适配器模式：");
        Phone phone = new Phone();
        phone.charging(new VoltageAdapter());
    }
}
