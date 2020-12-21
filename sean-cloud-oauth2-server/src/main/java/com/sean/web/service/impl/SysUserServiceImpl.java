package com.sean.web.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.web.entities.SysRole;
import com.sean.web.entities.SysUser;
import com.sean.web.mapper.SysRoleMapper;
import com.sean.web.mapper.SysUserMapper;
import com.sean.web.service.SysUserService;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	private static final String PASSWORD_DEFAULT = "$2a$10$rDkPvvAFV8kqwvKJzwlRv.i.q.wz1w1pz0SFsHn/55jNeZFQv/eCm";
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	// 通过用户名，查用户
	@Override
	public SysUser findByUsername(String username) {
		if(StringUtils.isEmpty(username)) {
			return null;
		}
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
		queryWrapper.eq("username", username);
		return baseMapper.selectOne(queryWrapper);

	}

	// 通过手机号，查用户
	@Override
	public SysUser findByMobile(String mobile) {
		if(StringUtils.isEmpty(mobile)) {
			return null;
		}
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
		queryWrapper.eq("mobile", mobile);
		return baseMapper.selectOne(queryWrapper);
	}

	// 分页(前端传入分页参数，并按用户名或手机号码查询)
	@Override
	public IPage<SysUser> customSelectPage(Page<SysUser> page, SysUser sysUser) {
		return baseMapper.customSelectPage(page, sysUser);
	}

	// 通过用户id，查询所属角色集合
	@Override
	public SysUser customGetRolesByUserId(Long userId) {
		if(userId == null ) {	//没传入userId则表示新增
			return new SysUser();
		}
		// 1. 通过用户id，查询用户
		SysUser sysUser = baseMapper.selectById(userId);
		// 2. 用户Id，查询所属角色
		List<SysRole> roleList = sysRoleMapper.customGetRolesByUserId(userId);
		// 3. 将所属角色，set到sysUser
		sysUser.setRoleList(roleList);
		return sysUser;
	}
	
	// 自定义更新user及所属角色
	@Override	//重写父类saveOrUpdate方法
	@Transactional	//开启事务管理
	public boolean saveOrUpdate(SysUser sysUser) {
		
		// 判断如果为新增用户
		if(sysUser != null && sysUser.getId() == null) {
			// 则设定模式密码为123
			sysUser.setPassword(PASSWORD_DEFAULT);
		}
		
		boolean flag = false;	//标记结果
		// 1. 更新sys_user表
//		sysUser.setUpdateDate(new Date());
		boolean ret = super.saveOrUpdate(sysUser);
		
		if(ret) { // sys_user表更新成功后，更新sys_user_role中的数据
			// 2. 先删除该用户所属角色
			baseMapper.customDeleteRoleByUserId(sysUser.getId());
			
			// 3. 再重新添加前端传过来的role Ids列表非空， 该重新新增该角色所属permission
			if(CollectionUtils.isNotEmpty(sysUser.getRoleIds())) {
				baseMapper.customSaveUserRole(sysUser.getId(), sysUser.getRoleIds());
			}
			flag = true;
		}
		return flag;
	}

	// 逻辑删除，is_enable=0
	@Override
	public boolean logicDeleteById(Long id) {
		SysUser sysUser = baseMapper.selectById(id);
		sysUser.setEnabled(false);
		sysUser.setUpdateDate(new Date());
		baseMapper.updateById(sysUser);
		return true;
	}
	
	

}
