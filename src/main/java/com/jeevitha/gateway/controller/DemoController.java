package com.jeevitha.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/api/demo")
    public String demo(){
        return "API Gateway Working!";
    }
}
