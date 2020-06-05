package com.wqzeng.springbtgradle.service;

import com.wqzeng.springbtgradle.model.dto.Person;
import org.junit.Test;

import java.util.Date;
import java.util.Optional;

public class ReferenceMethodTest {

    @Test
    public void listPerson() {
        ReferenceMethod referenceMethod=new ReferenceMethod();
        Optional<Person> optional=Optional.ofNullable(referenceMethod.listPerson().get(0));
    }

    @Test
    public void printDate() {
        ReferenceMethod referenceMethod=new ReferenceMethod();
        referenceMethod.printDate(new Date());
    }
}