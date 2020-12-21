package com.sean.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sean.web.entities.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser>{

	// 分页(前端传入分页参数，并按用户名或电话号码查询)
	IPage<SysUser> customSelectPage(Page<SysUser> page,@Param("user") SysUser sysUser);
	
	// 根据用户 id 删除所属角色(删除sys_user_role)
	boolean customDeleteRoleByUserId(@Param("userId") Long userId);
	
	// 根据用户id，保存所属角色 (添加sys_user_role)
	boolean customSaveUserRole(@Param("userId") Long userId, 
				@Param("roleIds") List<Long> roldIds);
}
