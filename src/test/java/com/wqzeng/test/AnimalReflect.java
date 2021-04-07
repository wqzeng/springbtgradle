package com.wqzeng.test;

import com.wqzeng.springbtgradle.model.dto.Animal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * 反射类
 */
public class AnimalReflect {
    Logger logger= LogManager.getLogger(this.getClass());
    Class<Animal> animalClass= Animal.class;
    public void listConstructors(){
        Constructor[] constructors=animalClass.getConstructors();
        for (Constructor constructor:constructors){
            logger.info("construtor name:{},paramerter count:{}",constructor.getName(),constructor.getParameterCount());
        }
    }

    public void listFields(){
        Field[] fields=animalClass.getDeclaredFields();
        for (Field field:fields){
            logger.info("field name:{}",field.getName());
        }
    }
}
