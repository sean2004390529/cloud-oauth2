package com.sean.web.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

public class SysRole implements Serializable{

    @TableId(type = IdType.AUTO)
    private Long id;
    
    // 角色名称
    private String name;
    
    // 角色描述
    private String remark;

    private Date createDate;
    private Date updateDate;
    
    
    /**
     * 存储当前角色的权限资源对象集合
     * 修改角色时用到
     */
    @TableField(exist = false)
    private List<SysPermission> perList = Lists.newArrayList();
    
    
    /**
     * 存储当前角色的权限资源ID集合
     * 修改角色时用到
     */
    @TableField(exist = false)
    private List<Long> perIds = Lists.newArrayList();
    
    public List<Long> getPerIds() {
        if(CollectionUtils.isNotEmpty(perList)) {
            perIds = Lists.newArrayList();
            for(SysPermission per : perList) {
                perIds.add(per.getId());
            }
        }
        return perIds;
    }

    
	/*
	 * setter & getter
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<SysPermission> getPerList() {
		return perList;
	}

	public void setPerList(List<SysPermission> perList) {
		this.perList = perList;
	}

	public void setPerIds(List<Long> perIds) {
		this.perIds = perIds;
	}


	@Override
	public String toString() {
		return "SysRole [id=" + id + ", name=" + name + ", remark=" + remark + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", perList=" + perList + ", perIds=" + perIds + "]";
	}
    
	
}
