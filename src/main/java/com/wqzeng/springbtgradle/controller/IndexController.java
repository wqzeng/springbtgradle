package com.wqzeng.springbtgradle.controller;

import com.wqzeng.springbtgradle.annotation.AutoPrintLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping("/ok")
    @AutoPrintLog
    public String ok(){
        return "ok";
    }
}
