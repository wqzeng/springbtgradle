package com.wqzeng.springbtgradle.pattern.strategy;

/**
 * 策略模式
 * Comparator也是函数式策略模式，可以用lamada来实现
 * @see java.util.Comparator
 */
@FunctionalInterface
public interface Strategy {
    public int doOperation(int num1, int num2);
}
