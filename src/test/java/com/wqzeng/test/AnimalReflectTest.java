package com.wqzeng.test;

import com.wqzeng.springbtgradle.util.DateUtils;
import org.junit.Assert;
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

    @Test
    public void nullTest() {
        DateUtils dateUtils=null;
        if (null == dateUtils) {
            Assert.assertTrue(null == dateUtils);
        }
    }
}