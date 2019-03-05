package com.xxyp.admin.server.service.merchant.mannger;

import java.util.List;

import com.xxyp.admin.common.model.merchant.MerchantRolePermission;

public interface MerchantRolePermissionManager {
	public List<MerchantRolePermission> queryRolePermissionListByRoleId(Long id);
	
	public List<MerchantRolePermission> queryRolePermissionList(MerchantRolePermission rolePermission);
	
	public List<Long> queryDistinctPermissionIdByRoleId(Long roleId);
	
	public List<Long> queryDistinctPermissionItemIdByRoleId(Long id);
	
	public void saveRolePermission(MerchantRolePermission rolePermission);
	
	public int deleteRolePermission(MerchantRolePermission rolePermission);
	
	
}
