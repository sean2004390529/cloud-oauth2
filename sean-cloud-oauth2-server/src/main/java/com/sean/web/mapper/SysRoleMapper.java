package com.sean.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sean.web.entities.SysRole;

public interface SysRoleMapper extends BaseMapper<SysRole> {

	// 分页(前端传入分页参数，并按角色名查询)
	IPage<SysRole> customSelectPage(Page<SysRole> page,@Param("role") SysRole sysRole);
	
	// 根据角色 id 删除所属权限(删除sys_role_permission)
	boolean customDeletePermissionByRoleId(@Param("roleId") Long roleId);
	
	// 根据角色id，保存所属权限 (添加sys_role_permission)
	boolean customSaveRolePermission(@Param("roleId") Long roleId, 
			@Param("permissionIds") List<Long> permissionIds);
	
	// 通过用户id，查询所属角色集合
	List<SysRole> customGetRolesByUserId(Long userId);
}