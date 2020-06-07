package com.wqzeng.test;

import com.alibaba.fastjson.JSON;
import com.wqzeng.springbtgradle.annotation.AutoPrintLog;
import com.wqzeng.springbtgradle.model.dto.Animal;
import com.wqzeng.springbtgradle.model.dto.Cat;
import com.wqzeng.springbtgradle.model.dto.Dog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 泛型使用
 */
public class GenType {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    @AutoPrintLog
    public List<? super Animal> add2List(){
        List<? super Animal> animals=new ArrayList<>();
        animals.add(new Animal("animal",0,""));
        animals.add(new Cat("helleKitty",2,""));
        animals.add(new Dog("big dog",3,""));
        return animals;
    }

    /**
     * <? extends T>表示的是类型的上界（包含自身），因此通配的参数化类型可能是T或T的子类。正因为无法确定具体的类型是什么，add方法受限（可以添加null，因为null表示任何类型）
     * @param animals
     */
    @AutoPrintLog
    public void add2ListByExtends(List<? extends Animal> animals){
        logger.info("animals[0]={}",JSON.toJSONString(animals.get(0)));
        logger.info("add2List animals={}",JSON.toJSONString(animals));
    }

    /**
     * 下界通配符<? super T>表示的是参数化类型是T的超类型（包含自身），层层至上，直至Object，编译器无从判断get()返回的对象的类型是什么，因此get()方法受限
     * @param dogs dog的父类或本身
     */
    public void add2ListBySuper(List<? super Dog> dogs){
        logger.info("dogs[0]={}",JSON.toJSONString(dogs.get(0)));
        logger.info("add2List dogs={}",JSON.toJSONString(dogs));
    }
    public void add2List(@NotNull List<Animal> animals){
        animals.add(new Dog("big dog",8,""));
        animals.add(new Dog("small dog",1,""));
        animals.add(new Cat("helloKitty",1,""));
        logger.info("animals:{}", JSON.toJSONString(animals));
        logger.info("animals's size:{}",animals.size());
    }
}
