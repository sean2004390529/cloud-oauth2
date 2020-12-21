package com.sean.web.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

@EnableTransactionManagement	//开启事务管理
@MapperScan("com.sean.web.mapper")
@Configuration
public class MybatisPlusConfig {
	
	@Bean
	public PaginationInterceptor pageInterceptor() {
		return new PaginationInterceptor();
	}
	
}
