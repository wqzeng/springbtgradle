package com.wqzeng.springbtgradle.pattern.prototype;

public class Circle extends Shape {
    public Circle() {
        type="Circle";
    }

    @Override
    void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
