package com.wqzeng.springbtgradle.pattern.singleton;

/**
 * 单例模式
 * 双检锁/双重校验锁（DCL，即 double-checked locking）
 */
public class Singleton {
    private volatile static Singleton singleton;
    private Singleton (){}
    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
