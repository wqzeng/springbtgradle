package com.wqzeng.springbtgradle.service;

import com.wqzeng.springbtgradle.model.dto.Person;

/**
 * 函数式接口
 * 只能包含一个抽象方法
 */
@FunctionalInterface
public interface NameChecker {
    boolean check(Person person);
}
