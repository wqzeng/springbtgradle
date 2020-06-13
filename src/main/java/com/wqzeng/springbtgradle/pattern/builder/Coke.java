package com.wqzeng.springbtgradle.pattern.builder;

public class Coke extends ColdDrink {
    @Override
    public String name() {
        return "coke";
    }

    @Override
    public float price() {
        return 30f;
    }
}
