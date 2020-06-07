package com.wqzeng.springbtgradle.model.dto;

import com.wqzeng.springbtgradle.annotation.RareAnimal;

@RareAnimal
public class Tiger extends Animal{
    public Tiger(String name, Integer age,String nickName) {
        super(name, age,nickName);
    }
}
