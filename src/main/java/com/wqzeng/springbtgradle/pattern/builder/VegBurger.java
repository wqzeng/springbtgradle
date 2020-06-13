package com.wqzeng.springbtgradle.pattern.builder;

public class VegBurger extends Burger {
    @Override
    public String name() {
        return "veg Burger";
    }

    @Override
    public float price() {
        return 2.50f;
    }
}
