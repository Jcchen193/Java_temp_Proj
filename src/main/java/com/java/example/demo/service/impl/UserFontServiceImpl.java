package com.java.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.java.example.demo.domain.UserFont;
import com.java.example.demo.mapper.UserFontMapper;
import com.java.example.demo.service.UserFontService;

@Service
@CacheConfig(cacheNames = "userFont")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class UserFontServiceImpl implements UserFontService {

    @Autowired
    UserFontMapper userFontMapper;

    private UserFont userFont;

    @Override
    public UserFont findById(Integer id) {

        if(id != null){
            userFont = userFontMapper.findById(id);
        }

        if(userFont != null){
            return userFont;
        }else{
            return null;
        }
    }

    @Override
    @Cacheable(key="#p0", unless = "#result == null")
    public UserFont findById2(Integer id) {
        System.out.println("当前 ID 为：" + id);
        Assert.notNull(id, "ID不为空。");
        return userFontMapper.findById(id);
    }


    @Nullable
    @Cacheable(value = "userFont", keyGenerator = "simpleKeyGenerator")
    public UserFont findByIdTtl(Integer id){
        System.out.println("当前 ID 为：" + id);
        Assert.notNull(id, "ID不为空。");
        return userFontMapper.findById(id);
    }
}
