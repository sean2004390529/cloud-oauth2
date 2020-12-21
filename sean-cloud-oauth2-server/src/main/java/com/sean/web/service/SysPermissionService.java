package com.sean.web.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sean.web.entities.SysPermission;

public interface SysPermissionService extends IService<SysPermission>{

	// 通过用户ID，查询所有权限
	List<SysPermission> selectPermissionByUserId(Long userId);

	// 通过权限id，删除权限及子权限
	boolean recurDeleteById(Long id);
}
