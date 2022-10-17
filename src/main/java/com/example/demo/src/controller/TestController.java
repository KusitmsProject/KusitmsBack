package com.example.demo.src.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
public class TestController {

    @GetMapping("/")
    public String time() {
        return LocalDateTime.now().toString();
    }

    @GetMapping("/test")
    public String test(){
        return "Welcome to KusitmsServer!";
    }
}