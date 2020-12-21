package com.sean.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sean.web.entities.SysRole;

public interface SysRoleService extends IService<SysRole>{

	// 分页(前端传入分页参数，并按角色名查询)
	IPage<SysRole> customSelectPage(Page<SysRole> page, SysRole sysRole);
	
	// 通过role id查询角色，及其该角色所拥有的权限
	SysRole customFindRoleAndPermission(Long roleId);
	
	// 通过role id删除角色，及其该角色所拥有的权限
	boolean customDeleteRoleAndPermission(Long roleId);
}
