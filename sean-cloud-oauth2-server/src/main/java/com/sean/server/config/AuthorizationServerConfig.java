package com.sean.server.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer	//开启认证服务器
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	// 配置password模式时，需要配置
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService customUserDetailsService;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	
	
	// 授权码存放到jdbc数据库 oauth_code,如果授权码使用了，则被删除
	@Bean
	public AuthorizationCodeServices jdbcAuthorizationCodeServices() {
		return new JdbcAuthorizationCodeServices(dataSource);
	}
	
	// 通过数据库查找 ClientDetails
	@Bean
	public ClientDetailsService jdbcClientDetailsService() {
		return new JdbcClientDetailsService(dataSource);
	}
	
	/*
	 * 允许访问此认证服务器的客户端信息
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// jdbc管理客户端
		clients.withClientDetails(jdbcClientDetailsService());
			   
	}

	/*
	 * 认证服务器端点配置
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);// 配置password模式时，需要配置
		endpoints.userDetailsService(customUserDetailsService);// 刷新令牌时，需要配置
		endpoints.tokenStore(tokenStore).accessTokenConverter(jwtAccessTokenConverter);	//配置令牌管理方式
		endpoints.authorizationCodeServices(jdbcAuthorizationCodeServices());// 授权码存放到jdbc数据库
	}

	/*
	 * 令牌端点的安全访问策略
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// 所有人可访问 /oauth/token_key 要获取公钥，默认拒绝
		security.checkTokenAccess("permitAll()");
		// 认证后可访问 /oauth/check_token 默认拒绝访问
		security.tokenKeyAccess("isAuthenticated()");
	}

	
	
	
}
