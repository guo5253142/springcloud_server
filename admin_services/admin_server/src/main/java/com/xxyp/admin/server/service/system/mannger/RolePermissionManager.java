package com.xxyp.admin.server.service.system.mannger;

import java.util.List;

import com.xxyp.admin.common.model.system.RolePermission;

public interface RolePermissionManager {
	public List<RolePermission> queryRolePermissionListByRoleId(Long id);
	
	public List<RolePermission> queryRolePermissionList(RolePermission rolePermission);
	
	public List<Long> queryDistinctPermissionIdByRoleId(Long roleId);
	
	public List<Long> queryDistinctPermissionItemIdByRoleId(Long id);
	
	public void saveRolePermission(RolePermission rolePermission);
	
	public int deleteRolePermission(RolePermission rolePermission);
	
	
}
