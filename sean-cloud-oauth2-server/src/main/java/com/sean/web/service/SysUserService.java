package com.sean.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sean.web.entities.SysUser;

public interface SysUserService extends IService<SysUser> {

	// 登录 - 通过用户名，查询用户
	SysUser findByUsername(String username);
	
	// 登录 - 通过电话号码，查询用户
	SysUser findByMobile(String mobile);
	
	// 分页(前端传入分页参数，并按用户名或手机号码查询)
	IPage<SysUser> customSelectPage(Page<SysUser> page, SysUser sysUser);
	
	// 通过用户id，查询所属角色集合
	SysUser customGetRolesByUserId(Long userId);

	// 逻辑删除，is_enable=0
	boolean logicDeleteById(Long id);
	
}
