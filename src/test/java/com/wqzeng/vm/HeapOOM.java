package com.wqzeng.vm;

import com.wqzeng.springbtgradle.entity.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * java堆溢出
 * 堆存储对象实例，不断创建对象，堆内存大小设为20MB不可扩展
 * VM Args:-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {
    public static void main(String[] args) {
        List<Goods> list = new ArrayList<>();
        while (true) {
            list.add(new Goods());
        }
    }
}
