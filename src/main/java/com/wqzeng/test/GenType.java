package com.wqzeng.test;

import com.wqzeng.springbtgradle.model.dto.Animal;
import com.wqzeng.springbtgradle.model.dto.Dog;

import java.util.ArrayList;
import java.util.List;

public class GenType {
    public void add2List(){
        List<? super Animal> animals=new ArrayList<>();

        animals.add(new Dog("big dog",3));
    }
}
