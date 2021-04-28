package com.java.example.demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.java.example.demo.security.filter.TokenAuthFilter;
import com.java.example.demo.security.filter.TokenLoginFilter;
import com.java.example.demo.security.security.DefaultPasswordEncoder;
import com.java.example.demo.security.security.TokenLogoutHandler;
import com.java.example.demo.security.security.TokenManager;
import com.java.example.demo.security.security.UnauthEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

	private TokenManager tokenManager;
    private RedisTemplate<String, Object> redisTemplate;
    private DefaultPasswordEncoder defaultPasswordEncoder;
    private UserDetailsService userDetailsService;
	  
    @Autowired
	public TokenWebSecurityConfig(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate,
			DefaultPasswordEncoder defaultPasswordEncoder, UserDetailsService userDetailsService) {
		super();
		this.tokenManager = tokenManager;
		this.redisTemplate = redisTemplate;
		this.defaultPasswordEncoder = defaultPasswordEncoder;
		this.userDetailsService = userDetailsService;
	}

    //调用userDetailService和密码处理器
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
	}

	//不进行认证的路径，可以直接访问
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/api/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http
//		.formLogin().loginPage("/login").and()
//		.exceptionHandling()
//			.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login.html")) //没有权限访问
//			.and()
			.csrf().disable()
			.authorizeRequests().antMatchers("/login").permitAll()
			.anyRequest().authenticated() 
			.and()
			.logout().logoutUrl("/admin/logout") //退出路径
			.addLogoutHandler(new TokenLogoutHandler(tokenManager, redisTemplate)) //加入退出处理器
			.and()
			.addFilter(new TokenLoginFilter(tokenManager, redisTemplate, authenticationManager())) //加入认证filter
			.addFilter(new TokenAuthFilter(tokenManager, redisTemplate, authenticationManager())) //加入授权filter
			.httpBasic();
	}

	
}
