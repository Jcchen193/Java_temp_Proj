package com.java.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.java.example.demo.RedisService;
import com.java.example.demo.domain.UserFont;
import com.java.example.demo.service.UserFontService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFontController {

    @Autowired
    private UserFontService userFontService;

    @Autowired
    private RedisService redisService;

    private final String redisKey = "test_";

    private final String rankKey = "rank_key_";
    
    private final String allKeysPattern = "*";

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

    @RequestMapping("/add_rank")
    public String addRank(String id, Double resource){
        redisService.zAdd(rankKey, id, resource);
        return "success";
    }

    @RequestMapping("/get_rank")
    public Set<Object> getRank(Double resource1, Double resource2){
        return redisService.rangeByScore(rankKey, resource1, resource2);
    }

    @RequestMapping("/get_rank_by_id")
    public Long getRank(String id){
        return redisService.zRank(rankKey, id);
    }

    @RequestMapping("/get_all_keys")
    public String getKeys(){
        Set<String> set = redisService.getKeys(allKeysPattern);
        return set.toString();
    }
    
    @RequestMapping("/validation_key_exist")
    public Long validationKey(String key) {
    	List list = new ArrayList<>();
    	list.add(key);
    	return redisService.isExits(list);
    }
    
    @RequestMapping("/del_key")
    public Long delKeys(String key) {
    	List list = new ArrayList<>();
    	list.add(key);
    	return redisService.delByKey(list);
    }
    
    @RequestMapping("/get_data_type")
    public DataType getDataType(String key) {
    	return redisService.getType(key);
    }
    
    @RequestMapping("/set_key_value")
    public String setString(String key, String value) {
    	redisService.set(key, value);
    	return "success";
    }
    
    @RequestMapping("/get_key")
    public Object getString(String key) {
    	return redisService.get(key);
    }

}
