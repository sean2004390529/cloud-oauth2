package com.sean.web.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

public class SysPermission implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 父权限 ID (0为顶级菜单)
     */
    private Long parentId = 0L ;
    
    /**
     * 用于新增和修改页面上默认的根菜单名称
     */
    @TableField(exist = false)
    private String parentName = "根菜单";
    
    // 权限名称
    private String name;
    
    // 授权标识符
    private String code;
    
    // 授权路径
    private String url;
    
    /**
     * 类型(1菜单，2按钮)
     */
    private Integer type;
    
    // 图标
    private String icon;
    
    // 备注
    private String remark;
    
    private Date createDate;
    private Date updateDate;
    
    /**
     * 所有子权限对象集合
     * 左侧菜单渲染时要用
     */
    @TableField(exist = false)
    private List<SysPermission> children;
    
    /**
     * 所有子权限 URL 集合
     * 左侧菜单渲染时要用
     */
    @TableField(exist = false)
    private List<String> childrenUrl;

    
	/*
	 * setter & getter
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<SysPermission> getChildren() {
		return children;
	}

	public void setChildren(List<SysPermission> children) {
		this.children = children;
	}

	public List<String> getChildrenUrl() {
		return childrenUrl;
	}

	public void setChildrenUrl(List<String> childrenUrl) {
		this.childrenUrl = childrenUrl;
	}

	@Override
	public String toString() {
		return "SysPermission [id=" + id + ", parentId=" + parentId + ", parentName=" + parentName + ", name=" + name
				+ ", code=" + code + ", url=" + url + ", type=" + type + ", icon=" + icon + ", remark=" + remark
				+ ", createDate=" + createDate + ", updateDate=" + updateDate + ", children=" + children
				+ ", childrenUrl=" + childrenUrl + "]";
	}
    
    
    
}
