package com.wqzeng.springbtgradle.interceptor;

import com.wqzeng.springbtgradle.controller.RedisController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {
    private Logger log = LoggerFactory.getLogger(LoginHandlerInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String value = (String) request.getSession().getAttribute("loginName");
        if(StringUtils.hasLength(value)){
            return true;
        }else{
            log.error("没有登录");
            return true;
        }
    }
}
