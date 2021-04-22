package com.java.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.java.example.demo.entity.Users;

@Mapper
public interface UsersMapper {

	Users getUsersByUserName(String userName);
}
