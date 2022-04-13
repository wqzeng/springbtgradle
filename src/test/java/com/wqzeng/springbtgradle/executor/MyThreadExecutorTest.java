package com.wqzeng.springbtgradle.executor;

import com.wqzeng.springbtgradle.service.Canteen;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyThreadExecutorTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CompletableFuture completableFuture1;
    @Autowired
    private ExecutorServiceFactory executorServiceFactory;

    @Test
    public void testExecutor() throws Exception {
        List<Canteen> canteenList = new ArrayList<>();
        for(int i=0;i<110;i++){
            Canteen canteen=new Canteen("canteen"+i);
            canteenList.add(canteen);
        }

        ExecutorService executorService = executorServiceFactory.getExecutorService();
//        在call方法中countDownLatch.countDown()
        CountDownLatch countDownLatch = new CountDownLatch(canteenList.size());

        List<Future<String>> futureList = new ArrayList<>();
        for (Canteen canteen : canteenList) {
            futureList.add(executorService.submit(()->{
                try {
                    canteen.sell();
                    return canteen.getName()+":OK";
                } finally {
                    countDownLatch.countDown();
                }
            }));
//            futureList.add(executorService.submit(new AbstractCountDownCallable<String>(countDownLatch) {
//                @Override
//                protected String executeMethod() throws Exception {
//                    canteen.sell();
//                    return canteen.getName()+":OK";
//                }
//            }));
        }
        try {
            countDownLatch.await();
            logger.info("并发处理完成");
        } catch (InterruptedException e) {
            logger.error("并发处理中断",e);
        }
//        try {
//            if (countDownLatch.await(2000, TimeUnit.MICROSECONDS)) {
//                logger.info("并发处理完成");
//            }else{
//                logger.info("并发处理超时");
//            }
//        } catch (InterruptedException e) {
//            logger.error("并发处理中断",e);
//        }
        for (Future<String> future : futureList) {
            logger.info(future.get());
        }
    }

    @Test
    public void testFutures() throws Exception {
        ExecutorService executorService = executorServiceFactory.getExecutorService();
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
                }, executorService
        );
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("r 10");
            return 10;
        }, executorService).thenApplyAsync((e)->e-1);
        System.out.println(completableFuture2.get());
        completableFuture2.join();
        CompletableFuture.allOf(completableFuture1, completableFuture2).join();

        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync((e) -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    return e * 10;
                }).thenApplyAsync(e -> e - 1)
                .whenComplete((r, e)->{
                    System.out.println("done");
                })
                ;

        cf.join();
        System.out.println(cf.get());
    }
}
