package com.wqzeng.springbtgradle.service;

import com.wqzeng.springbtgradle.model.dto.Person;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

public class MyLambdaInterfaceImplTest {


    @Test
    public void doSomething() {
        MyLambdaInterface myLambdaInterface = new MyLambdaInterfaceImpl();
        myLambdaInterface.doSomething("I am a lambda");
    }

    @Test
    public void doAnotherThing() {
        //lambda表达式就是函数式接口的实现
        MyLambdaInterface lambda = (s) -> System.out.println(s);
        lambda.doSomething("I am a lambda");
        MyLambdaInterface lambda2 = s -> {
            String[] a = s.split(",");
            System.out.println(a.length);
        };
        lambda2.doSomething("RED,GREEN,WHITE");
    }

    @Test
    public void checkAndExecute() {
        List<Person> personList=Lists.newArrayList(
                new Person("YiXing1","Zhao",32),
                new Person("YiXing2","Zhao",32),
                new Person("YangGui","Li",30),
                new Person("Chao","Li",35));
        MyLambdaInterfaceImpl myLambda = new MyLambdaInterfaceImpl();
        myLambda.checkAndExecute(personList,person -> person.getLastName().startsWith("Z"),person -> System.out.println(person.getFirstName()));
        myLambda.checkAndExecute2(personList,p -> p.getLastName().startsWith("L"),p -> System.out.println(p.getFirstName()));
        personList.stream().filter(person -> person.getLastName().startsWith("L")).forEach(p-> System.out.println(p.getFirstName()));

        Predicate<Person> predicate=person -> person.getLastName().startsWith("L");
        Predicate<Person> predicate1=person -> person.getAge()>30;
        personList.stream().filter(predicate.and(predicate1)).forEach(p-> System.out.println(p.getFirstName()));
        List<Person> result=personList.stream().filter(predicate.and(predicate1.negate())).collect(Collectors.toList());
        result.forEach(person -> System.out.println(person.getFirstName()));
    }
}