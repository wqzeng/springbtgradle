package com.wqzeng.springbtgradle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Canteen implements Producer{
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    public void sell() {
        logger.info("小卖部进行卖货");
    }
}
