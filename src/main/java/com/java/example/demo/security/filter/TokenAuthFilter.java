package com.java.example.demo.security.filter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.java.example.demo.security.security.TokenManager;

//授权过滤器
public class TokenAuthFilter extends BasicAuthenticationFilter {

	private TokenManager tokenManager;
	
	private RedisTemplate<String, Object> redisTemplate;
	
	public TokenAuthFilter(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate, AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.tokenManager = tokenManager;
		this.redisTemplate = redisTemplate;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//获取当前认证用户信息
		UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
		//判断如果有权限信息，放到权限上下文中
		if(authenticationToken != null) {
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		chain.doFilter(request, response);
	}
	
	public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		//从header获取token
		String token = request.getHeader("token");
		if(token != null) {
			//获取用户信息
			String userName = tokenManager.getUserInfoFromToken(token);
			//从redis获取权限信息
			List<String> permissionVlaueList = (List<String>) redisTemplate.opsForValue().get(userName);
			//将权限列表转换成Collection<GrantedAuthority>
			Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			
			for(String permission : permissionVlaueList) {
				SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission);
				grantedAuthorities.add(simpleGrantedAuthority);
			}
			
			return new UsernamePasswordAuthenticationToken(userName, token, grantedAuthorities);
		}
		return null;
		
	}
	
	
	

}
