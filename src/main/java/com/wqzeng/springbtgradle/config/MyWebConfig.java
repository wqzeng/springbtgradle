package com.wqzeng.springbtgradle.config;

import com.wqzeng.springbtgradle.interceptor.LoginHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginHandlerInterceptor loginHandlerInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //指定不拦截的地址，不会经过拦截器
        String[] excludePath ={"**/session/set?**"};
        registry.addInterceptor(loginHandlerInterceptor).excludePathPatterns(excludePath);
    }
}
