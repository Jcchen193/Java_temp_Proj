package com.java.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.java.example.demo.domain.UserFont;

@Mapper
public interface UserFontMapper {

    UserFont findById(@Param("id") Integer id);

}