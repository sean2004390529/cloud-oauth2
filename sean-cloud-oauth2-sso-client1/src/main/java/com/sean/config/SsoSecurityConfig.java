package com.sean.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

@Configuration
@EnableOAuth2Sso	//开启单点登录
public class SsoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public OAuth2RestTemplate oAuth2RestTemplate(
			UserInfoRestTemplateFactory factory) {
		return factory.getUserInfoRestTemplate();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.anyRequest().authenticated()
		.and()
			.logout().logoutSuccessUrl("http://localhost:7001/auth/logout")
		.and()
			.csrf().disable()
			
			;
	}

	
}
