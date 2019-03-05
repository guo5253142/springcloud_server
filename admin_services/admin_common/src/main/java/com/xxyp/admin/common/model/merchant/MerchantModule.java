package com.xxyp.admin.common.model.merchant;

import java.util.List;

import com.xxyp.admin.common.model.BasePO;

public class MerchantModule extends BasePO {

    private static final long serialVersionUID = 4767307740910429540L;

    private Long id;

    private String name;

    private String remark;
    

    private List<MerchantPermission> permissionList;
    
    private Integer orderIndex;

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

    public List<MerchantPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<MerchantPermission> permissionList) {
        this.permissionList = permissionList;
    }

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

}
