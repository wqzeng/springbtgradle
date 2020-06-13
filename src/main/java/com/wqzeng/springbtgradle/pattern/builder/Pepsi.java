package com.wqzeng.springbtgradle.pattern.builder;

public class Pepsi extends ColdDrink {
    @Override
    public String name() {
        return "pepsi";
    }

    @Override
    public float price() {
        return 40f;
    }
}
