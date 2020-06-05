package com.wqzeng.springbtgradle.service;

import com.wqzeng.springbtgradle.model.dto.Person;

@FunctionalInterface
public interface Executor {
    void execute(Person person);
}
