package com.sean.resource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/*
 * 配置资源服务器
 */
@Configuration
@EnableResourceServer	//标识为资源服务器，请求服务器中的资源，就要带着token过来
@EnableGlobalMethodSecurity(prePostEnabled = true)	//开启方法级别权限控制
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	public static final String RESOURCE_ID = "product-server";
	
	@Autowired
	private TokenStore tokenStore;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// 认证服务器会认证客户端是否有访问这个资源ID的权限
		resources.resourceId(RESOURCE_ID).tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //SpringSecurity不会创建也不使用HttpSession实例
		.and()
			.authorizeRequests()
			.antMatchers("/product/list").hasAuthority("sys:user:list")
			.antMatchers("/**").access("#oauth2.hasScope('PRODUCT_API')");	//所有请求都需要scope指定的访问
	}
	
}
