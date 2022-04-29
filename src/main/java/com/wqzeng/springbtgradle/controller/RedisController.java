package com.wqzeng.springbtgradle.controller;

import com.wqzeng.springbtgradle.annotation.AutoPrintLog;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
//@Slf4j
@RestController
@RequestMapping("/redis")
@EnableRedisHttpSession
public class RedisController {
    private Logger log = LoggerFactory.getLogger(RedisController.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/write")
    public Map<String, String> write(@RequestParam String key,@RequestParam String value) {
        Map<String, String> result = new HashMap<>();
        result.put(key, value);
        stringRedisTemplate.opsForValue().multiSet(result);
        return result;
    }

    @GetMapping("/read")
    public Map<String, String> read(@RequestParam String key) {
        Map<String, String> result = new HashMap<>();
        result.put(key, stringRedisTemplate.opsForValue().get(key));
        return result;
    }

    @GetMapping("/mapWrite")
    @AutoPrintLog
    public Boolean mapWrite(@RequestParam String key,@RequestParam String field,@RequestParam String value) {
        Boolean result=redisTemplate.opsForHash().putIfAbsent(key, field,value);
        return result;
    }

    @GetMapping("/mapRead")
    @AutoPrintLog
    public Object mapRead(@RequestParam String key,@RequestParam String field) {
        Object result=redisTemplate.opsForHash().get(key, field);
        return result;
    }

    @GetMapping("/keys")
    @AutoPrintLog
    public Set allKeys(@RequestParam String pattern) {
        Set result=redisTemplate.keys(pattern);
        return result;
    }

    @GetMapping("/hasKey")
    @AutoPrintLog
    public Boolean hasKey(@RequestParam String pattern) {
        Boolean result=redisTemplate.hasKey(pattern);
        return result;
    }

    @GetMapping("/session/set")
//    @AutoPrintLog
    public Map<String,String> setSession(@RequestParam String key,@RequestParam String value,HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        result.put(key, value);
        request.getSession().setAttribute(key,value);
        return result;
    }
    @GetMapping("/session/get")
//    @AutoPrintLog
    public Map<String, String> getSession(@RequestParam String key,HttpServletRequest request) {
        String value = (String) request.getSession().getAttribute(key);
        log.info("get session key:{},value:{}",key,value);
        Map<String, String> result = new HashMap<>();
        result.put(key, value);
        return result;
    }
}
