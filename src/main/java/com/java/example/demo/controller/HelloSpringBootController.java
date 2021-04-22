package com.java.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSpringBootController{

    @RequestMapping("/helloSpringBoot")
    @ResponseBody
    public String hello(){
        return "<b>Hello Spring Boot!</b>";
    }

}