package com.wqzeng.springbtgradle.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MyThreadExecutor extends ThreadPoolExecutor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String name;
    private int queueSize;

    /**
     * keepAliveTime 当线程数大于corePoolSize时，keepAliveTime起作用
     * @param name
     * @param corePoolSize
     * @param queueSize
     */
    public MyThreadExecutor(String name,int corePoolSize, int queueSize) {
        super(corePoolSize, corePoolSize+queueSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(queueSize),new CallerRunsPolicy());
        this.name=name;
        this.queueSize=queueSize;

        register();
    }

    private void register(){
        synchronized (this) {
            MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName objectName;
            try {
                String classInfo = "MyThreadExecutor_" + name+"|MBean";
                objectName = new ObjectName("executor:type="+classInfo);
                if (!mbeanServer.isRegistered(objectName)) {
                    mbeanServer.registerMBean(this, objectName);
                    if (logger.isDebugEnabled()) {
                        logger.debug("executor:type=" + classInfo + " registered successfully");
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
        }
    }
}
