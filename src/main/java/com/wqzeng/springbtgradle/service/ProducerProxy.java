package com.wqzeng.springbtgradle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 静态代理 代理类在程序运行前就已经存在,那么这种代理方式被成为静态代理
 * 定义产家的代理商,也具备卖货的功能
 */
public class ProducerProxy implements Producer{
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    private Producer producer;
    ProducerProxy(Producer producer){
        this.producer=producer;
    }

    @Override
    public void sell() throws InterruptedException {
        logger.info("小卖部卖货前");
        producer.sell();
        logger.info("小卖部卖货后");
    }
}