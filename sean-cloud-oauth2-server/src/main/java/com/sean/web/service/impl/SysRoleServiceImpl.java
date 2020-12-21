package com.sean.web.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.web.entities.SysPermission;
import com.sean.web.entities.SysRole;
import com.sean.web.mapper.SysPermissionMapper;
import com.sean.web.mapper.SysRoleMapper;
import com.sean.web.service.SysRoleService;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{

	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	
	// 分页(前端传入分页参数，并按角色名查询)
	@Override
	public IPage<SysRole> customSelectPage(Page<SysRole> page, SysRole sysRole) {
		return baseMapper.customSelectPage(page, sysRole);
	}

	// 通过role id查询角色，及其该角色所拥有的权限
	@Override
	public SysRole customFindRoleAndPermission(Long roleId) {
		// 如果没传入roleId，表示未新增角色
		if(roleId == null) {
			return new SysRole();
		}
		// 1. 通过role id 查询出角色
		SysRole sysRole = baseMapper.selectById(roleId);
		// 2. 通过role id 查询该角色的权限
		List<SysPermission> permissonList = sysPermissionMapper.selectPermissionByRoleId(roleId);
		// 3. 将拥有权限set到角色对象的属性List<Permission>上
		sysRole.setPerList(permissonList);
		return sysRole;
	}

	// 自定义更新role及所属权限
	@Override	//重写父类saveOrUpdate方法
	@Transactional	//开启事务管理
	public boolean saveOrUpdate(SysRole sysRole) {
		boolean flag = false;	//标记结果
		// 1. 更新sys_role表
//		sysRole.setUpdateDate(new Date());
		boolean ret = super.saveOrUpdate(sysRole);
		
		if(ret) { // sys_role表更新成功后，更新sys_role_permission中的数据
			// 2. 先删除该角色所属permission，
			baseMapper.customDeletePermissionByRoleId(sysRole.getId());
			
			// 3. 再重新添加前端传过来的permission Ids列表非空， 该重新新增该角色所属permission
			if(CollectionUtils.isNotEmpty(sysRole.getPerIds())) {
				baseMapper.customSaveRolePermission(sysRole.getId(), sysRole.getPerIds());
			}
			flag = true;
		}
		return flag;
	}

	// 通过role id删除角色，及其该角色所拥有的权限
	@Override
	@Transactional
	public boolean customDeleteRoleAndPermission(Long roleId) {
		baseMapper.deleteById(roleId);
		baseMapper.customDeletePermissionByRoleId(roleId);
		return true;
	}

}
