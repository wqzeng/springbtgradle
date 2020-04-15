package com.wqzeng.test;

import com.wqzeng.springbtgradle.annotation.AutoPrintLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrimeThread extends Thread {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    public void run() {
//        super.run();
        logger.info("PrimeThread run:{}",Thread.currentThread().getName());
    }
}
