package com.java.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.example.demo.service.CreateThreadService;

import java.util.List;

@RestController
//@RequestMapping(value = "/thread")
public class CreateThreadController {

	@Autowired
    private CreateThreadService threadService;

    @RequestMapping("/createThread/byThread")
    @ResponseBody
    public List<String> createThreadByThread(){
    	List<String> resulsts = threadService.getThreadByThread();
        return resulsts;
    }
    
    @RequestMapping("/createThread/byRunable")
    @ResponseBody
    public List<String> createThreadByRunable(){
    	List<String> resulsts = threadService.getThreadByRunable();
        return resulsts;
    }
    
    
    @RequestMapping("/createThread/byCallable")
    @ResponseBody
    public List<String> createThreadByCallable(){
    	List<String> resulsts = threadService.getThreadByCallable();
        return resulsts;
    }
    
    @RequestMapping("/createThread/mutipleThread")
    @ResponseBody
    public List<String> createMutipleThreadByThread(){
    	List<String> resulsts = threadService.getMultipleThreadByThread();
        return resulsts;
    }
    
    @RequestMapping("/createThread/concurrentMapTest")
    @ResponseBody
    public List<String> concurrentMapTest(){
    	List<String> resulsts = threadService.getConcurrentMapTest();
        return resulsts;
    }

    @RequestMapping("/createThread/copyOnWriteTest")
    @ResponseBody
    public List<String> copyOnWriteTest(){
    	List<String> resulsts = threadService.getCopyOnWriteTest();
        return resulsts;
    }
}
