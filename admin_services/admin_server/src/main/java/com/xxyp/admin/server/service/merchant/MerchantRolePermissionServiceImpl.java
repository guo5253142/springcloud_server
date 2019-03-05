package com.xxyp.admin.server.service.merchant;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxyp.admin.common.model.merchant.MerchantRolePermission;
import com.xxyp.admin.common.service.merchant.MerchantRolePermissionService;
import com.xxyp.admin.server.annotation.WriteDataSource;
import com.xxyp.admin.server.service.merchant.mannger.MerchantRolePermissionManager;
@Service
public class MerchantRolePermissionServiceImpl implements MerchantRolePermissionService {
	@Autowired
	public MerchantRolePermissionManager rolePermissionManager;

	@WriteDataSource
	public List<MerchantRolePermission> queryRolePermissionListByRoleId(Long roleId) {
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
	public List<MerchantRolePermission> queryRolePermissionList(MerchantRolePermission rolePermission) {
		return rolePermissionManager.queryRolePermissionList(rolePermission);
	}

	@WriteDataSource
	public void saveRolePermission(MerchantRolePermission rolePermission) {
		 rolePermissionManager.saveRolePermission(rolePermission);
	}

	@WriteDataSource
	public int deleteRolePermission(MerchantRolePermission rolePermission) {
		return rolePermissionManager.deleteRolePermission(rolePermission);
	}
}
