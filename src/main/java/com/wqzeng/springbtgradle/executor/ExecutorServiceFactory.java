package com.wqzeng.springbtgradle.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author moke.tsang
 */
@Service
public class ExecutorServiceFactory implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorServiceFactory.class);

    private static ExecutorService executorService;
    private final static int MAX_SIZE=15;

    @Override
    public void afterPropertiesSet() throws Exception {
//        从配置中获取线程池数
        int poolSize=Math.max(MAX_SIZE,Runtime.getRuntime().availableProcessors());
        int queueSize=1024;
        logger.info("初始化线程池大小:{}，线程队列大小:{}",poolSize,queueSize);
        setExecutorService("测试多线程",poolSize,queueSize);
    }
    @PreDestroy
    protected void destory(){
        shutDown(executorService,"多线程Factory");
    }
    public static  ExecutorService getExecutorService() throws Exception {
        if(ExecutorServiceFactory.executorService ==null){
            //        从配置中获取线程池数
            int poolSize=Math.min(MAX_SIZE,Runtime.getRuntime().availableProcessors() );
            int queueSize=30;
            logger.info("初始化线程池大小:{}，最大线程池大小：{},线程队列大小:{}",poolSize,MAX_SIZE,queueSize);
            setExecutorService("测试多线程",poolSize,queueSize);
        }
        return ExecutorServiceFactory.executorService;
    }
    private static synchronized  void setExecutorService(String name,int corePoolSize,int queueSize){
        if(ExecutorServiceFactory.executorService !=null && !ExecutorServiceFactory.executorService.isShutdown()){
            logger.error("executorService已经存在，不能再初始化");
            return ;
        }
//        executorService = Executors.newFixedThreadPool(corePoolSize,new ThreadFactoryBuilder().setNameFormat("thread-%d-"+name).build());
//        executorService = Executors.newScheduledThreadPool(corePoolSize,new ThreadFactoryBuilder().setNameFormat("thread-%d-"+name).build());
        executorService = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("thread-%d-"+name).build());
//        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
//                .setNameFormat("thread-pool-%d-"+name).build();
//        executorService = new ThreadPoolExecutor(corePoolSize, MAX_SIZE,
//                1000L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<>(queueSize), namedThreadFactory,new ThreadPoolExecutor.CallerRunsPolicy());
    }
    private void shutDown(ExecutorService executorService , String executorName){
        if(executorService!=null && !executorService.isShutdown()){
            logger.info("开始释放线程池：{}",executorName);
            executorService.shutdown();
            logger.info("释放线程池：{}成功",executorName);
        }
    }
}
