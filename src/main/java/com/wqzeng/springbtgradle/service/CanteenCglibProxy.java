package com.wqzeng.springbtgradle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CanteenCglibProxy implements MethodInterceptor {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    private Canteen canteen = new Canteen();

    CanteenCglibProxy(Canteen canteen) {
        this.canteen = canteen;
    }

    Canteen proxy() {
        // 创建Enhancer对象
        Enhancer enhancer = new Enhancer();
        // 设置代理的目标类
        enhancer.setSuperclass(Canteen.class);
        // 设置回调方法, this代表是当前类, 因为当前类实现了CallBack
        enhancer.setCallback(this);
        return (Canteen) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        logger.info("----------小卖部卖货前--------");
        Object invoke = method.invoke(canteen,objects);
        logger.info("----------小卖部卖货后--------");
        return invoke;
    }
}
