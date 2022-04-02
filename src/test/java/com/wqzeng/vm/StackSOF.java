package com.wqzeng.vm;

/**
 * 虚拟机栈和本地方法栈溢出
 * VM Args：-Xss128k
 * -Xss栈内存容量
 */
public class StackSOF {
    private int stackLength=1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        StackSOF stackSOF=new StackSOF();
        try {
            stackSOF.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:"+stackSOF.stackLength);
            throw e;
        }
    }
}
