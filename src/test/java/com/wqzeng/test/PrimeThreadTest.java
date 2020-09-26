package com.wqzeng.test;

import com.wqzeng.springbtgradle.model.dto.Ticket;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class PrimeThreadTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    PrimeThread primeThread=new PrimeThread();

    @Test
    public void run() {
        primeThread.setName("PrimeThreadTest");
        primeThread.start();
        logger.info("primeThread status:{}",primeThread.getState());
        Runnable runnable=()-> System.out.println("ThreadTest is run");
        Thread thread=new Thread(runnable);
        thread.run();
        logger.info("thread status:{}",thread.getState());
    }

    @Test
    public void sell(){
        Ticket ticket=new Ticket();
        Thread threadA=new Thread(ticket::sell);
        threadA.setName("sellerA");
        Thread threadB=new Thread(ticket::sell);
        threadB.setName("sellerB");
        Thread threadC=new Thread(ticket::sell);
        threadC.setName("sellerC");
        threadA.start();
        threadB.start();
        threadC.start();
    }

    @Test
    public void future() throws InterruptedException, ExecutionException {
        Future<Integer> future=primeThread.future();
        Integer i=future.get();
        System.out.println("I="+i);
    }
}