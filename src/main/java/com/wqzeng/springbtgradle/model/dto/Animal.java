package com.wqzeng.springbtgradle.model.dto;

import com.wqzeng.springbtgradle.annotation.RareAnimal;
import lombok.Data;

import java.io.Serializable;

@Data
public class Animal implements Serializable {
    public Animal(String name, Integer age,String nickName) {
        this.name = name;
        this.age = age;
        this.nickName=nickName;
    }

    private String name;
    private Integer age;
    /**
     * transient型变量的值不包括在序列化的表示中
     */
    private transient String nickName;

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nickName=" + nickName +
                '}';
    }
}
