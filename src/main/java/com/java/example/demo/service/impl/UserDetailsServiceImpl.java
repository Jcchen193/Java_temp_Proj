package com.java.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.java.example.demo.entity.Users;
import com.java.example.demo.mapper.UsersMapper;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

//	@Override
//	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//		String userName = "admin";
//		String password = "12345";
//		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("admin");
//		return new User(userName, new BCryptPasswordEncoder().encode(password), authorities);
//	}

	@Autowired
	UsersMapper usersMapper;  
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {;
		Users users = usersMapper.getUsersByUserName(username);
		if(users == null) {
			throw new UsernameNotFoundException("User Not Found");
		}
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(users.getRole());
		return new User(users.getUserName(), new BCryptPasswordEncoder().encode(users.getPassword()), authorities);
	}

}
