package com.wqzeng.springbtgradle.pattern.builder;

public class ChickenBurger extends Burger {
    @Override
    public String name() {
        return "chicken Burger";
    }

    @Override
    public float price() {
        return 50.5f;
    }
}
