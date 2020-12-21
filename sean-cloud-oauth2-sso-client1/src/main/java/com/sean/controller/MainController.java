package com.sean.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sean.base.result.DataResult;

@Controller
public class MainController {

	@Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/member")
	public String member() {
		ResponseEntity<DataResult> entity = oAuth2RestTemplate.getForEntity("http://localhost:7001/product/list", DataResult.class);
		System.out.println("client-member-/member " + entity.getBody());
		return "member";
	}
}
