package com.java.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.example.demo.entity.Users;


@RestController
@RequestMapping("/login")
public class LoginController {

	@GetMapping("/hello")
	public String hello(HttpRequest request) {
		
		return "hello Spring Security";
	}
	
	@GetMapping("/testSecured")
	//使用@Secured注解需要开启注解功能@EnableGlobalMethodSecurity(securedEnabled=true)
	@Secured({"ROLE_admin", "ROLE_managed"}) //角色有admin 和 managed权限才能调用,角色名需要加前缀ROLE_
	public String testSecured() {
		return "testSecured";
	}
	
	@GetMapping("/testPreAuthorize")
	//使用@PreAuthorize注解需要开启注解功能@EnableGlobalMethodSecurity(prePostEnabled=true)
	@PreAuthorize("hasAnyAuthority('admin', 'manager')") //进入方法前做权限验证
	public String testPreAuthorize() {
		return "testPreAuthorize";
	}
	
	@GetMapping("/testPostAuthorize")
	//使用@PostAuthorize注解需要开启注解功能@EnableGlobalMethodSecurity(prePostEnabled=true)
	@PostAuthorize("hasAnyAuthority('admin', 'manager')") //执行方法后做权限验证
	public String testPostAuthorize() {
		return "testPostAuthorize";
	}
	
	
	@GetMapping("/testPreFilter")
	@PreFilter("filterObject.name=='张三'") //对传入的数据做过滤
	public List<Users> testPreFilter(@RequestBody List<Users> users) {
		return users;
	}
	
	@GetMapping("/testPostFiltere")
	@PostFilter("filterObject.name=='张三'") //对返回的数据做过滤
	public List<Users> testPostFilter() {
		List<Users> users = new ArrayList<Users>();
		users.add(new Users(1, "23213", "张三", "admin"));
		users.add(new Users(1, "23213", "李四", "admin"));
		return users;
	}
	
	@PostMapping("/update")
	
	public String update() {
		return "修改成功";
	}

}
