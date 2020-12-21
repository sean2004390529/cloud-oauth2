package com.sean.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.web.entities.SysPermission;
import com.sean.web.mapper.SysPermissionMapper;
import com.sean.web.service.SysPermissionService;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService{

	// 通过用户ID，查询所有权限
	@Override
	public List<SysPermission> selectPermissionByUserId(Long userId) {
		if(userId == null) {
			return null;
		}
		List<SysPermission> permissionList = baseMapper.selectPermissionByUserId(userId);
		
		//避免某个用户什么权限都没有，避免创建了包含了一个null权限的size为1的List
		permissionList.remove(null);
		return permissionList;
	}

	// 通过权限id，删除权限及子权限
	@Transactional //开启事务
	@Override
	public boolean recurDeleteById(Long id) {
		// 1. 删除子权限（parent_id为当前id的权限）
		LambdaQueryWrapper<SysPermission> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysPermission::getParentId, id);
		baseMapper.delete(queryWrapper);
		
		// 2. 删除当前id的权限
		baseMapper.deleteById(id);
		return true;
	}
	
	

}
