package com.wqzeng.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class PrimeThread extends Thread {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
//        super.run();
        logger.info("PrimeThread run:{}", Thread.currentThread().getName());
    }

    public Future<Integer> future() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Task task=new Task();
        Future<Integer> future=executorService.submit(task);
        return future;
    }

    public FutureTask<Integer> futureTask() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Task task=new Task();
        FutureTask<Integer> futureTask = new FutureTask<>(task);
        executorService.submit(futureTask);
        return futureTask;
    }

    class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            // 模拟计算需要⼀秒
            Thread.sleep(1000);
            return 2;
        }
    }

    public static void main(String[] args) {
        //注入钩子线程，退出时会执行
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.printf("The hook thread is running.");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }
}
