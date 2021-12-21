package com.mycompany.BackOffice.security;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import com.mycompany.BackOffice.service.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("Run HttpSecurity http");
		
		//로그인 설정
		http.formLogin()
			.loginPage("/") 						//default: /login (GET)
			.loginProcessingUrl("/login")			//default: /login (POST)
			.failureUrl("/loginFail")				//default: /login?error
			.defaultSuccessUrl("/dashboard/main")
			.usernameParameter("memberId")			//default: username
			.passwordParameter("password");			//default: password
		
		//로그아웃 설정
		http.logout()
			.logoutUrl("/logout")					//defualt: /logout
			.logoutSuccessUrl("/");
		
		//URL 권한 설정
		http.authorizeRequests()
			.antMatchers("/brand/**").authenticated()
			.antMatchers("/dashboard/**").authenticated()
			.antMatchers("/design/**").authenticated()
			.antMatchers("/member/**").authenticated()
			.antMatchers("/order/**").authenticated()
			.antMatchers("/product/**").authenticated()
			.antMatchers("/promotion/**").authenticated()
			.antMatchers("/").permitAll();
		
		//권한 없음(403)일 경우 이동할 경로 설정
		http.exceptionHandling().accessDeniedPage("/");
		
		//CSRF(사이트 요청 위조 방지) 비활성화
		http.csrf().disable();
	}
	
	@Resource
	private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		log.info("Run passwordEncoder()");
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("Run AuthenticationManagerBuilder");
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		auth.authenticationProvider(provider);
		
	}
	
	@Bean
	public RoleHierarchyImpl roleHierarchyImpl() {
		log.info("Run roleHierarchyImpl()");
		
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy("ROLE_ADMIN > ROLE_MANAGER");
		
		return roleHierarchyImpl;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		log.info("Run WebSecurity web");
		
		DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
		handler.setRoleHierarchy(roleHierarchyImpl());
		web.expressionHandler(handler);
		
		web.ignoring()
		   .antMatchers("/bootstrap-4.6.0-dist/**")
		   .antMatchers("/css/**")
		   .antMatchers("/images/**")
		   .antMatchers("/jquery/**")
		   .antMatchers("/favicon.ico");
	}
	
}
