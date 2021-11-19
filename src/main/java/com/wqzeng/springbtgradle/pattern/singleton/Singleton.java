package com.wqzeng.springbtgradle.pattern.singleton;

/**
 * 单例模式 类加载到内存中只实例化一个单例
 * 双检锁/双重校验锁（DCL，即 double-checked locking）
 */
public class Singleton {
    /**
     * 懒汉式
     */
    private volatile static Singleton singleton;

    /**
     * 设置构造方法私有的
     */
    private Singleton() {
    }

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
