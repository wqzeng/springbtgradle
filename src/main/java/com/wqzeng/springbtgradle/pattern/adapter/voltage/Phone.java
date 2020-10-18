package com.wqzeng.springbtgradle.pattern.adapter.voltage;

public class Phone {
    public void charging(Voltage5V voltage5V)throws Exception{
        if(voltage5V.output5V()==5){
            System.out.printf("电压为5V，可以充电");
        }else if(voltage5V.output5V()>5){
            throw new Exception("电压大于5V，无法充电");
        }
    }
}
