package com.java.example.demo.security.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * token操作工具类
 * 使用JWT生成Token
 */
@Component
public class TokenManager {

	//设置token失效时长
	private int tokenExpireTime = 24*60*60*1000;
	
	//设置token加密密钥
	private String tokenSignKey = "yzx12345";
	
	//使用JWT根据用户名生成token
	public String createToken(String userName) {
		String token = Jwts.builder().setSubject(userName)
							.setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime)) //设置token实现时间
							.signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
		return token;
	}
	
	//从token中拿到用户信息	
	public String getUserInfoFromToken(String token) {
		String userName = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
		return userName;
	}
	
	//删除token
	public void removeToken(String token) {
		
	}
	
	public static void main(String[] args) {
		TokenManager tManager = new TokenManager();
		System.out.println(tManager.createToken("zexiang.yan"));
	}
}
