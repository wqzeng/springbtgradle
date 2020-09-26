package com.wqzeng.springbtgradle.model.dto;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ticket {
    private Logger logger=LoggerFactory.getLogger(this.getClass());
    private int num=10;

    private Lock lock= new ReentrantLock();

    public void sell(){
        while (num>0){
            lock.lock();
            try {
                logger.info("sellor:{} sell ticket{}",Thread.currentThread().getName(),num);
            } finally {
                lock.unlock();
            }
            num--;
        }
        logger.info("sellor:{} sell out ticket{}",Thread.currentThread().getName(),num);
    }

    /**
     * 临界区卖票,线程独占卖票
     */
    public synchronized void sellBySync(){
        while (num>0){
            logger.info("sellor:{} sell ticket{}",Thread.currentThread().getName(),num);
            num--;
        }
        logger.info("sellor:{} sell out ticket{}",Thread.currentThread().getName(),num);
    }

    /**
     * 临界区卖票,线程独占卖票
     */
    public void sellBySync2(){
        while (num>0){
            synchronized (this){//临界区
                if(num>0){
                    logger.info("sellor:{} sell ticket{}",Thread.currentThread().getName(),num);
                    num--;
                }
            }
            try{
                TimeUnit.MILLISECONDS.sleep(10L);
            }catch (InterruptedException e){
                logger.error("sleep fault",e);
            }
        }
        logger.info("sellor:{} sell out ticket{}",Thread.currentThread().getName(),num);
    }
    public static void main(String[] args) {
        Object o=new Object();
        o.notify();
        Ticket ticket=new Ticket();
        Thread threadA=new Thread(ticket::sellBySync2);
        threadA.setName("sellerA");
        Thread threadB=new Thread(ticket::sellBySync2);
        threadB.setName("sellerB");
        Thread threadC=new Thread(ticket::sellBySync2);
        threadC.setName("sellerC");
        threadA.start();
        threadB.start();
        threadC.start();
    }
}
