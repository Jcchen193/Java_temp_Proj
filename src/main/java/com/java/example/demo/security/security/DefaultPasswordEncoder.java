package com.java.example.demo.security.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.java.example.demo.util.utils.MD5;
/*
 * 密码处理
 */
@Component
public class DefaultPasswordEncoder implements PasswordEncoder {
	

	public DefaultPasswordEncoder() {
		this(-1);
	}
	public DefaultPasswordEncoder(int strenth) {
		
	}
	
	//加密
	@Override
	public String encode(CharSequence rawPassword) {		
		return MD5.encrypt(rawPassword.toString());
	}

	//判断密码是否相等
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
//		return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
		return encodedPassword.equals(rawPassword);
	}

}
