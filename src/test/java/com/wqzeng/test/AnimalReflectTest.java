package com.wqzeng.test;

import org.junit.Test;

public class AnimalReflectTest {
    AnimalReflect animalReflect=new AnimalReflect();
    @Test
    public void listConstructors() {
        animalReflect.listConstructors();
    }
    @Test
    public void listFields(){
        animalReflect.listFields();
    }
}