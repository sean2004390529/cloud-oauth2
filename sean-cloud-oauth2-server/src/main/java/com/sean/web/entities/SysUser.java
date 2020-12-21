package com.sean.web.entities;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

public class SysUser implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO) // 表示主键自增长
    private Long id;
	
    private String username;

    private String password;
    
    /**
     * 帐户是否有效：1 未过期，0已过期
     */
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;
    
    private String nickName;
    private String mobile;
    private String email;
    private Date createDate;
    private Date updateDate;
    

    @TableField(exist = false)    //它不是sys_user表中的属性
    private Collection<? extends GrantedAuthority> authorities;
	
    /**
     * 拥有角色集合
     */
    @TableField(exist = false)
    private List<SysRole> roleList = Lists.newArrayList();
    
    /**
     * 获取所有角色id
     */
    @TableField(exist = false)
    private List<Long> roleIds = Lists.newArrayList();
    public List<Long> getRoleIds() {
        if(CollectionUtils.isNotEmpty(roleList)) {
            roleIds = Lists.newArrayList();
            for(SysRole role : roleList) {
                roleIds.add(role.getId());
            }
        }
        return roleIds;
    }
    
    
    /**
     * 封装当前用户拥有的权限资源对象
     */
    @TableField(exist = false)
    private List<SysPermission> permissions = Lists.newArrayList();
	public Long getId() {
		return id;
	}

	
	
	/*
	 * setter & getter
	 */

	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}


	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}


	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}


	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}


	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}


	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}


	public boolean isEnabled() {
		return isEnabled;
	}


	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateDate() {
		return createDate;
	}



	public Date getUpdateDate() {
		return updateDate;
	}



	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}



	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}



	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}


	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}


	public List<SysRole> getRoleList() {
		return roleList;
	}


	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}


	public List<SysPermission> getPermissions() {
		return permissions;
	}


	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}


	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}



	@Override
	public String toString() {
		return "SysUser [id=" + id + ", username=" + username + ", password=" + password + ", isAccountNonExpired="
				+ isAccountNonExpired + ", isAccountNonLocked=" + isAccountNonLocked + ", isCredentialsNonExpired="
				+ isCredentialsNonExpired + ", isEnabled=" + isEnabled + ", nickName=" + nickName + ", mobile=" + mobile
				+ ", email=" + email + ", createDate=" + createDate + ", updateDate=" + updateDate + ", authorities="
				+ authorities + ", roleList=" + roleList + ", roleIds=" + roleIds + ", permissions=" + permissions
				+ "]";
	}



    
}
