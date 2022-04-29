package com.wqzeng.springbtgradle.config;

import com.wqzeng.springbtgradle.aspect.AutoPrintLogAspect;
import com.wqzeng.springbtgradle.callback.Fetcher;
import com.wqzeng.springbtgradle.callback.FetcherImpl;
import com.wqzeng.springbtgradle.callback.Worker;
import com.wqzeng.springbtgradle.entity.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionConfig {
    /**
     * windows 环境才加aspect
     * @return
     */
    @Bean
    @Conditional(GpCondition.class)
    public AutoPrintLogAspect autoPrintLogAspect() {
        return new AutoPrintLogAspect();
    }
    @Bean
    public Worker getFetcher() {
        return new Worker();
    }
}
