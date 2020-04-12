package com.wqzeng.springbtgradle.model.dto;

import com.wqzeng.springbtgradle.annotation.AnimalDescription;
import lombok.Data;

@Data
public class Animal {
    public Animal(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @AnimalDescription("some animal name")
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