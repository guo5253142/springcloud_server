package com.xxyp.admin.server.service.system;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxyp.admin.common.model.system.RolePermission;
import com.xxyp.admin.common.service.system.RolePermissionService;
import com.xxyp.admin.server.annotation.WriteDataSource;
import com.xxyp.admin.server.service.system.mannger.RolePermissionManager;
@Service
public class RolePermissionServiceImpl implements RolePermissionService {
	@Autowired
	public RolePermissionManager rolePermissionManager;

	@WriteDataSource
	public List<RolePermission> queryRolePermissionListByRoleId(Long roleId) {
		if (roleId == null) {
			return null;
		}
		return rolePermissionManager.queryRolePermissionListByRoleId(roleId);
	}

	@WriteDataSource
	public List<Long> queryDistinctPermissionIdByRoleId(Long roleId) {
		if (roleId == null) {
			return null;
		}
		return rolePermissionManager.queryDistinctPermissionIdByRoleId(roleId);
	}

	@WriteDataSource
	public List<RolePermission> queryRolePermissionList(RolePermission rolePermission) {
		return rolePermissionManager.queryRolePermissionList(rolePermission);
	}

	@WriteDataSource
	public void saveRolePermission(RolePermission rolePermission) {
		 rolePermissionManager.saveRolePermission(rolePermission);
	}

	@WriteDataSource
	public int deleteRolePermission(RolePermission rolePermission) {
		return rolePermissionManager.deleteRolePermission(rolePermission);
	}
}
