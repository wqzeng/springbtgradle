package com.wqzeng.springbtgradle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController {
    /**
     * GET 从指定的资源请求数据
     * POST 向指定的资源提交要被处理的数据
     * @return
     */
    @GetMapping("/get_something")
    public String getSomething(HttpServletRequest request){
        return "ok";
    }


}
