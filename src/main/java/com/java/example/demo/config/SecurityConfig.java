package com.java.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	private DataSource dataSource; //注入数据源
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
//		jdbcTokenRepositoryImpl.setCreateTableOnStartup(true); //每次启动时自动创建记录rememberMe数据的表persistent_logins，默认关闭，可以自己创建
		return jdbcTokenRepositoryImpl;
	}
	/**
	 * 自定义
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.logout().logoutUrl("/logout") //配置退出的URL
			.logoutSuccessUrl("/login.html").permitAll(); //退出成功后跳转页面
		
		http.exceptionHandling().accessDeniedPage("/error/403.html"); //自定义403页面没跳转路径
		
		
		
		http.formLogin()
			.loginPage("/template/login.html")  //登录页面设置
			.loginProcessingUrl("/login/login")  //登录访问路径
			.defaultSuccessUrl("/template/main.html").permitAll() //登录成功之后跳转路径
			.and().authorizeRequests()
				.antMatchers("/login/login", "/css/**","/fonts/**","/images/**","/js/**").permitAll() //设置不需要拦截的路径
//				.antMatchers("/main.html").hasAuthority("admin") //设置main.html必须有admin role才能访问
//				.antMatchers("/main.html").hasAnyAuthority("admin", "manager") //设置main.html必须有admin或者 manager role才能访问
//				//hasRole和hasAnyRole在设置role的时候必须加上前缀ROLE_
//				.antMatchers("/main.html").hasRole("admin") //设置main.html必须有ROLE_admin role才能访问
//				.antMatchers("/main.html").hasAnyRole("admin, manager") //设置main.html必须有ROLE_admin或者ROLE_manager role才能访问
				.anyRequest().authenticated() //设置其他所有的都需要验证
				.and().rememberMe().tokenRepository(persistentTokenRepository())
					.tokenValiditySeconds(60*60*24*10)  //设置Token有效时长
					.userDetailsService(userDetailsService);
				http.csrf().disable(); //关闭csrf防护
			
	}
	
	
	
//	/**
//	 * 通过配置配置方法设置用户名密码。
//	 * @return
//	 */
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("admin");
//	}
}
