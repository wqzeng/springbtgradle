package com.wqzeng.vm;

import java.util.HashSet;
import java.util.Set;

/**
 * 运行时常量池内存溢出 jdk8 原本存放在永久代的字符串常量被移至java堆之中
 * jdk7:-XX:PermSize=6M -XX:MaxPermSize=6M
 * jdk8:-XX:MaxMetaspaceSize=6M
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        //使用set保持着常量池引用，避免Full GC回收常量池
        Set<String> set = new HashSet<>();
        //在short范围内足以让6M产生OOM
        short i = 0;
        while (true) {
            set.add(String.valueOf(i++).intern());
        }
    }
}
