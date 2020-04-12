package com.wqzeng.springbtgradle.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

public class ProducerProxyTest {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Test
    public void sell() {
        Producer producer=new Canteen();
//        静态代理
        ProducerProxy producerProxy=new ProducerProxy(producer);
        producerProxy.sell();
    }

    @Test
    public void sellByJDKProxy() {
        Producer producer=new Canteen();
        //动态代理 代理类在程序运行时创建的代理方式被称为 动态代理
        //如果目标对象实现了接口,采用JDK的动态代理，如果目标对象没有实现接口,必须采用cglib动态代理
        Producer producerProxy=(Producer) Proxy.newProxyInstance(producer.getClass().getClassLoader(),
                producer.getClass().getInterfaces(), (proxy, method, args1) -> {
                    logger.info("----------小卖部卖货前--------");
                    Object invoke = method.invoke(producer,args1);
                    logger.info("----------小卖部卖货后--------");
                    return invoke;
                });
        producerProxy.sell();
    }

    @Test
    public void sellByCgligProxy() {
        Canteen canteen=new Canteen();
        //动态代理 代理类在程序运行时创建的代理方式被称为 动态代理
        //如果目标对象实现了接口,采用JDK的动态代理，如果目标对象没有实现接口,必须采用cglib动态代理
        Canteen canteenProxy=new CanteenCglibProxy(canteen).proxy();
        canteenProxy.sell();
    }
}