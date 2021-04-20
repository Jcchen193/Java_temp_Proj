package com.java.example.demo.service;

import com.java.example.demo.domain.UserFont;

public interface UserFontService {

    UserFont findById(Integer id);

    UserFont findById2(Integer id);

    UserFont findByIdTtl(Integer id);

}
