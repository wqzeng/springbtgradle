package com.wqzeng.springbtgradle.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public abstract class AbstractCountDownCallable<T> implements Callable<T> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CountDownLatch latch;
    private final long start;

    protected AbstractCountDownCallable(CountDownLatch latch) {
        this.latch = latch;
        this.start=System.currentTimeMillis();
    }

    @Override
    public T call() throws Exception {
        try {
            T t =  executeMethod();
            return t;
        } finally {
//            logger.info("当前线程耗时：{}",System.currentTimeMillis()-start);
            latch.countDown();
        }
    }

    /**
     * 待实现执行方法
     * @return
     * @throws Exception
     */
    protected abstract T executeMethod() throws Exception;
}
