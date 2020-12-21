package com.sean.server.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sean.web.entities.SysPermission;
import com.sean.web.entities.SysUser;
import com.sean.web.service.SysPermissionService;

/*
 * 自定义抽取认证方法
 */
public abstract class AbstractUserDetailsService implements UserDetailsService {

	// 让子类实现，查询用户
	public abstract SysUser findSysUser(String usernameOrMobile);
	
	@Autowired
	private SysPermissionService sysPermissionService;
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrMobile) throws UsernameNotFoundException {
		// 1.让子类实现查询用户的方法
		SysUser sysUser = findSysUser(usernameOrMobile);
		
		// 2. 调用内部方法
		findSysPermission(sysUser);
		
		return sysUser;
		
	}

	// 通过用户id，获取并封装用户权限
	private void findSysPermission(SysUser sysUser) {
		if(sysUser == null) {
			throw new UsernameNotFoundException("请确认用户名或密码或手机号码！！！");
		}
		
		// 2. 查询用户权限
		List<SysPermission> permissionList = sysPermissionService.selectPermissionByUserId(sysUser.getId());
		if(CollectionUtils.isEmpty(permissionList)) {
			return ;	//该用户没权限，则直接返回用户
		}

		// 3.1 额外：将权限信息，存入sysUser上的permissions集合
		// Themleaf会使用此权限，渲染页面，真是项目考虑是否需要
		sysUser.setPermissions(permissionList);
		
		// 3.2 封装用户权限信息
		List<GrantedAuthority> authorities = Lists.newArrayList();
		for(SysPermission permission: permissionList) {
			String permissionCode = permission.getCode();
			authorities.add(new SimpleGrantedAuthority(permissionCode));
		}
		sysUser.setAuthorities(authorities);
		
		// 4. Springsecurity自动进行身份认证

	}
	
	
}
