package com.sean.web.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sean.base.result.DataResult;

@RestController
@RequestMapping("/product")
public class ProductController {

//	@PreAuthorize("hasAuthority('sys:user:list')")
	@GetMapping("/list")
	public DataResult list(Principal principal) {
		List<String> list = new ArrayList<>();
		list.add("商品1");
		list.add("商品2");
		list.add(principal.getName());
		return DataResult.ok(list);
	}
}
