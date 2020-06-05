package com.wqzeng.springbtgradle.service;

/**
 * 只有一个接口函数需要被实现的接口类型，我们叫它”函数式接口“
 * 加注解@FunctionInterface 接口只能有一个函数
 */
@FunctionalInterface
public interface MyLambdaInterface {
    void doSomething(String s);
//    void doAnotherThing(String s);
}
