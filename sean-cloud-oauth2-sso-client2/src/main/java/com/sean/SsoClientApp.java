package com.sean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SsoClientApp {

	public static void main(String[] args) {
		SpringApplication.run(SsoClientApp.class, args);
	}
}
