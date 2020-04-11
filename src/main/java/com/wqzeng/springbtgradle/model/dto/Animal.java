package com.wqzeng.springbtgradle.model.dto;

import lombok.Data;

@Data
public abstract class Animal {
    public Animal(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    private String name;
    private Integer age;

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
