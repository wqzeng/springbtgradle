package com.wqzeng.springbtgradle.executor;

import com.wqzeng.springbtgradle.service.Canteen;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MyThreadExecutorTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CompletableFuture completableFuture1;

    @Test
    public void testExecutor() throws Exception {
        List<Canteen> canteenList = new ArrayList<>();

        ExecutorService executorService = ExecutorServiceFactory.getExecutorService();
//        在call方法中countDownLatch.countDown()
        CountDownLatch countDownLatch = new CountDownLatch(canteenList.size());

        List<Future<String>> futureList = new ArrayList<>();
        for (Canteen canteen : canteenList) {
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
        for (Future<String> future : futureList) {
            Assert.assertEquals(future.get(), "OK");
        }
    }

    @Test
    public void testFutures() throws Exception {
        ExecutorService executorService = ExecutorServiceFactory.getExecutorService();
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
