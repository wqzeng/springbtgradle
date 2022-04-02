package com.wqzeng.springbtgradle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Canteen implements Producer{
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    private String name;

    public Canteen(){
    }
    public Canteen(String name) {
        this.name=name;
    }
    @Override
    public void sell() throws InterruptedException {
        Thread.sleep(1);
        logger.info("{}:小卖部进行卖货",name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
