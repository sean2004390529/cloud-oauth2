package com.sean.server.config;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import com.alibaba.druid.pool.DruidDataSource;

import ch.qos.logback.core.net.ssl.KeyStoreFactoryBean;

@Configuration
public class TokenConfig {

	// 1. 对称加密
//	public static final String SIGNINGKEY = "Sean-encry-P@ssw0rd-key";
	
	// 配置数据源，项目需要移出此配置
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return new DruidDataSource();
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		// 1. 对称加密
//		converter.setSigningKey(SIGNINGKEY); //指定对称加密密钥
		// 2. 非对称加密
		KeyStoreKeyFactory factory = new KeyStoreKeyFactory(
				new ClassPathResource("oauth2.jks"), //密钥证书路径+名称
				"oauth2".toCharArray()); //密钥的keypass
		converter.setKeyPair(factory.getKeyPair("oauth2"));	//oauth2 --密钥的alias
		return converter;
	}
	
	@Bean
	public TokenStore tokenStore() {
		// 可选token管理方式：redis, jdbc, jwt
//		return new RedisTokenStore(redisConnectionFactory); //redis方式
//		return new JdbcTokenStore(dataSource());
		return new JwtTokenStore(jwtAccessTokenConverter()); //jwt方式管理令牌
	}
}
