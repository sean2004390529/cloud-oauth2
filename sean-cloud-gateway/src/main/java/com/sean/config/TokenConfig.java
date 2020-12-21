package com.sean.config;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class TokenConfig {

	// 1. 对称加密
//	public static final String SIGNINGKEY = "Sean-encry-P@ssw0rd-key";
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		// 1. 对称加密
//		converter.setSigningKey(SIGNINGKEY); //指定对称加密密钥
		// 2. 非对称加密
		String publicKey = null;
		ClassPathResource resource = new ClassPathResource("public.key");
		try {
			publicKey = IOUtils.toString(resource.getInputStream(), "UTF-8");
		} catch (IOException e) {
			System.out.println(e);
		}
		converter.setVerifierKey(publicKey);
		return converter;
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter()); //jwt方式管理令牌
	}
}
