package com.wqzeng.springbtgradle.pattern.singleton;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingletonTest {
    private  final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Test
    public void testGetInstance() {
        logger.info("testGetInstance");
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                logger.info(Singleton.getSingleton().toString());
            }).start();
        }
    }
}
