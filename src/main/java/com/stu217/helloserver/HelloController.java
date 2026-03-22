package com.stu217.helloserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        // 接口返回内容，可自定义
        return "Hello Spring Boot 3.x! This is my first server API.";
    }
}
