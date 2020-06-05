package com.wqzeng.springbtgradle.service;

import com.wqzeng.springbtgradle.model.dto.Person;

/**
 * 函数式接口
 * <p>Predicate</p>
 */
@FunctionalInterface
public interface NameChecker {
    boolean check(Person person);
}
