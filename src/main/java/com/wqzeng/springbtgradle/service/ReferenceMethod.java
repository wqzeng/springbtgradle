package com.wqzeng.springbtgradle.service;

import com.wqzeng.springbtgradle.model.dto.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReferenceMethod {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    public static List<Person> listPerson() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("YiXing", "Zhao", 32));
        personList.add(new Person("YangGui", "Li", 30));
        personList.add(new Person("Chao", "Li", 35));
        return personList;
    }

    public void printDate(Date date){
        logger.info("Today is {}",date.toString());
    }
}
