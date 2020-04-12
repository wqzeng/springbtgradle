package com.wqzeng.test;

import com.wqzeng.springbtgradle.model.dto.Animal;
import com.wqzeng.springbtgradle.model.dto.Cat;
import com.wqzeng.springbtgradle.model.dto.Dog;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GenTypeTest {
    Animal animal = new Cat("animal", 100);
    Dog dog = new Dog("big dog", 8);
    Cat cat = new Cat("helleKitty", 1);
    GenType genType = new GenType();

    @Test
    public void add2List() {
        List animals = genType.add2List();
        Assert.assertEquals("animals add2List fault", 3, animals.size());
        Assert.assertEquals(new Dog("big dog", 3), animals.get(2));
    }

    @Test
    public void add2List2() {
        List<Animal> animals = new ArrayList<>();
        genType.add2List(animals);
        Assert.assertEquals("animals size fault", 3, animals.size());
        List<Dog> dogs = new ArrayList<>();
        dogs.add(dog);
//        genType.add2List(dogs);
    }

    @Test
    public void add2ListByExtends() {
        List<Animal> animals = new ArrayList<>();
        animals.add(animal);
        animals.add(dog);
        animals.add(cat);
        genType.add2ListByExtends(animals);
        Assert.assertEquals("animals size fault", 3, animals.size());

        List<Dog> dogs = new ArrayList<>();
        dogs.add(dog);
        genType.add2ListByExtends(dogs);

        List<Cat> cats=new ArrayList<>();
        cats.add(cat);
        genType.add2ListByExtends(cats);
        genType.add2ListBySuper(animals);
        genType.add2ListBySuper(dogs);
        
    }
}