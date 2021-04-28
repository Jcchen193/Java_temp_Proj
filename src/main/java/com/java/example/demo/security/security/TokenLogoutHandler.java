package com.java.example.demo.security.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.java.example.demo.util.utils.R;
import com.java.example.demo.util.utils.ResponseUtil;

//退出处理器
public class TokenLogoutHandler implements LogoutHandler {

	
	private TokenManager tokenManager;
	
	private RedisTemplate<String, Object> redisTemplate;
	
	
	
	public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate) {
		this.tokenManager = tokenManager;
		this.redisTemplate = redisTemplate;
	}



	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		//从header获取token
		String token = request.getHeader("token");		
		//token不为空移除token，并从redis删除
		if(token != null) {
			tokenManager.removeToken(token);
			String userName = tokenManager.getUserInfoFromToken(token);
			redisTemplate.delete(userName);
		}
		ResponseUtil.out(response, R.ok());
	}

}
