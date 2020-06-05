package com.wqzeng.springbtgradle.service;

import com.wqzeng.springbtgradle.model.dto.Person;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MyLambdaInterfaceImpl implements MyLambdaInterface,NameChecker,Executor {
    @Override
    public void doSomething(String s) {
        System.out.printf(s);
    }

    @Override
    public boolean check(Person person) {
        return person.getLastName().startsWith("Z");
    }

    @Override
    public void execute(Person person) {
        System.out.printf(person.getFirstName());
    }

    public void checkAndExecute(List<Person> personList,NameChecker checker,Executor executor){
        for (Person person:personList) {
            if(checker.check(person)){
                executor.execute(person);
            }
        }
    }

    public void checkAndExecute2(List<Person> personList, Predicate<Person> predicate, Consumer<Person> consumer){
        for (Person person:personList){
            if(predicate.test(person)){
                consumer.accept(person);
            }
        }
    }
}
