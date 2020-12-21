package com.sean.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sean.web.entities.SysUser;
import com.sean.web.service.SysUserService;

/*
 * 查询数据库中的用户信息
 */
@Component("customUserDetailsService")
//public class CustomUserDetailsService implements UserDetailsService{
public class CustomUserDetailsService extends AbstractUserDetailsService{
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public SysUser findSysUser(String username) {
		
		// 1.通过请求的用户名，去数据库查找用户
		return sysUserService.findByUsername(username);

	}

}
