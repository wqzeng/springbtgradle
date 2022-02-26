package com.wqzeng.springbtgradle.pattern.strategy.storage;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StorageServiceFactory implements InitializingBean, ApplicationContextAware {
    private static Map<String, IStorageService> STORAGE_SERVICE_MAP = new HashMap<>(2);
    private ApplicationContext applicationContext;
    @Override
    public void afterPropertiesSet() throws Exception {
        //将Spring容器中的所有IStorageService注删到Map
        applicationContext.getBeansOfType(IStorageService.class).values().forEach(
                iStorageService -> STORAGE_SERVICE_MAP.put(iStorageService.getType(),iStorageService)
        );
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    public static IStorageService getStorageService(String type){
        return STORAGE_SERVICE_MAP.get(type);
    }
}
