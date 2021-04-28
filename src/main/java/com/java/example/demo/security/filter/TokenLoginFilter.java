package com.java.example.demo.security.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.java.example.demo.security.entity.SecurityUser;
import com.java.example.demo.security.entity.User;
import com.java.example.demo.security.security.TokenManager;
import com.java.example.demo.util.utils.R;
import com.java.example.demo.util.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

//认证过滤器
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

	private TokenManager tokenManager;	
	private RedisTemplate<String, Object> redisTemplate;
	private AuthenticationManager authenticationManager;
	
	
	
	public TokenLoginFilter(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate,
			AuthenticationManager authenticationManager) {
		super();
		this.tokenManager = tokenManager;
		this.redisTemplate = redisTemplate;
		this.authenticationManager = authenticationManager;
		this.setPostOnly(true); //限制Post提交
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login", "POST")); //设置登陆路径是Post
	}

	//获取表单提交的用户名密码
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			User user = new ObjectMapper().readValue(request.getInputStream(), User.class); //获取用户信息
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} 
	}

	//认证成功调用的方法
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		//获取用户信息
		SecurityUser securityUser = (SecurityUser) authResult.getPrincipal(); 
		//根据用户名生成token
		String token = tokenManager.createToken(securityUser.getCurrentUserInfo().getUsername()); 
		//把用户名和权限列表放入redis
		redisTemplate.opsForValue().set(securityUser.getCurrentUserInfo().getUsername(), securityUser.getPermissionValueList().toString()); 
		//返回token
		ResponseUtil.out(response, R.ok().data("token", token));
		
	}
	
	//认证失败调用的方法
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		ResponseUtil.out(response, R.error());
	}

	
}
