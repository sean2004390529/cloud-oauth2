package com.sean.server.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sean.web.entities.SysUser;
import com.sean.web.service.SysUserService;


/*
 * 通过手机号，获取用户信息与权限资源
 */
@Component("mobileUserDetailsService")
//public class MobileUserDetailsService implements UserDetailsService {
public class MobileUserDetailsService extends AbstractUserDetailsService {
	
	@Autowired
	private SysUserService sysUserService;

	@Override
	public SysUser findSysUser(String mobile) {
		System.out.println("mobileUserDetailsService 手机号：" + mobile);
		// 1. 通过手机号查询用户信息
		return sysUserService.findByMobile(mobile);
	}
	
}
