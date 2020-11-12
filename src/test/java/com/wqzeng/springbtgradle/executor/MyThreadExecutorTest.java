package com.wqzeng.springbtgradle.executor;

import com.wqzeng.springbtgradle.service.Canteen;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MyThreadExecutorTest {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Test
    public void testExecutor()throws Exception{
        List<Canteen> canteenList = new ArrayList<>();

        ExecutorService executorService=ExecutorServiceFactory.getExecutorService1();
//        在call方法中countDownLatch.countDown()
        CountDownLatch countDownLatch = new CountDownLatch(canteenList.size());

        List<Future<String>> futureList = new ArrayList<>();
        for (Canteen canteen:canteenList){
            futureList.add(executorService.submit(new AbstractCountDownCallable<String>(countDownLatch) {
                @Override
                protected String executeMethod() throws Exception {
                    canteen.sell();
                    return "OK";
                }
            }));
        }

        if (countDownLatch.await(30, TimeUnit.SECONDS)) {
            logger.error("并发处理超时");
        }
        for (Future<String> future:futureList){
            Assert.assertEquals(future.get(),"OK");
        }
    }
}