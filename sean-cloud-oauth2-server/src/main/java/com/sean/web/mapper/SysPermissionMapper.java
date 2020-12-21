package com.sean.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sean.web.entities.SysPermission;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {

	List<SysPermission> selectPermissionByUserId(@Param("userId") Long userId);
	
	List<SysPermission> selectPermissionByRoleId(@Param("roleId") Long roleId);
}
