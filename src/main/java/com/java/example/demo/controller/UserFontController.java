package com.java.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.java.example.demo.RedisService;
import com.java.example.demo.domain.UserFont;
import com.java.example.demo.service.UserFontService;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFontController {

    @Autowired
    private UserFontService userFontService;

    @Autowired
    private RedisService redisService;

    private final String redisKey = "test_";

    @RequestMapping("/find_user_by_id")
    public UserFont findUserById(Integer id){
        //UserFont userFont = JSONObject.parseObject(redisService.get(redisKey + id).toString(), UserFont.class);
        //UserFont userFont = (UserFont) redisService.get(redisKey + id);

        //if(userFont == null){

            UserFont userFont = userFontService.findById(id);

            if(userFont == null){
                return null;
            }else {
                //redisService.set(redisKey + id, JSON.toJSON(userFont));
                //redisService.set(redisKey + id, userFont);
                return userFont;
            }

        //}

        //return userFont;
    }

    @RequestMapping("/get_user_cache")
    public UserFont findUserById2(Integer id){
        return userFontService.findById2(id);
    }

    @RequestMapping("/get_user_cache_ttl")
    public UserFont findUserByIdTtl(Integer id){
        return userFontService.findByIdTtl(id);
    }

}
