package com.wqzeng.springbtgradle.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;

@Service
public class ExecutorServiceFactory implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorServiceFactory.class);

    private static ExecutorService executorService1;

    @Override
    public void afterPropertiesSet() throws Exception {
//        从配置中获取线程池数
        int poolSize=Math.max(20,Runtime.getRuntime().availableProcessors() * 2);
        int queueSize=200;
        logger.info("初始化线程池大小:{}，线程队列大小:{}",poolSize,queueSize);
        setExecutorService1("测试多线程",poolSize,queueSize);
    }
    @PreDestroy
    protected void destory(){
        shutDown(executorService1,"测试多线程");
    }
    public static  ExecutorService getExecutorService1() throws Exception {
        if(ExecutorServiceFactory.executorService1==null){
            //        从配置中获取线程池数
            int poolSize=Math.max(20,Runtime.getRuntime().availableProcessors() * 2);
            int queueSize=200;
            logger.info("初始化线程池大小:{}，线程队列大小:{}",poolSize,queueSize);
            setExecutorService1("测试多线程",poolSize,queueSize);
        }
        return ExecutorServiceFactory.executorService1;
    }
    private static synchronized  void setExecutorService1(String name,int size,int queueSize){
        if(ExecutorServiceFactory.executorService1!=null && !ExecutorServiceFactory.executorService1.isShutdown()){
            logger.error("executorService1已经存在，不能再初始化");
            return ;
        }
        ExecutorServiceFactory.executorService1 = new MyThreadExecutor(name, size, queueSize);
    }
    private void shutDown(ExecutorService executorService , String executorName){
        if(executorService!=null && !executorService.isShutdown()){
            logger.info("开始释放线程池：{}",executorName);
            executorService.shutdown();
            logger.info("释放线程池：{}成功",executorName);
        }
    }
}
