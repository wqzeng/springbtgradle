package com.wqzeng.test;

import com.wqzeng.springbtgradle.model.dto.Ticket;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrimeThreadTest {

    PrimeThread primeThread=new PrimeThread();

    @Test
    public void run() {
        primeThread.setName("PrimeThreadTest");
        primeThread.start();
        Runnable runnable=()-> System.out.println("ThreadTest is run");
        Thread thread=new Thread(runnable);
        thread.run();
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
}