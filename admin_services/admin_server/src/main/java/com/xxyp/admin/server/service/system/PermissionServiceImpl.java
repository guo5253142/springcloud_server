package com.xxyp.admin.server.service.system;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.system.Permission;
import com.xxyp.admin.common.service.system.PermissionService;
import com.xxyp.admin.server.annotation.WriteDataSource;
import com.xxyp.admin.server.service.system.mannger.PermissionManager;
@Service
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	public PermissionManager permissionManager;

	@WriteDataSource
	public List<Permission> queryPermission(Permission permission) {
		return permissionManager.queryPermissionList(permission);
	}

	@WriteDataSource
	public PageResult<Permission> queryPermissionList(DataPage<Permission> page) {
		page = permissionManager.queryPermissionList(page, page.getDataObj());
		PageResult<Permission> result = new PageResult<Permission>();
		result.setPage(page);
		return result;
	}

	@WriteDataSource
	public Permission queryPermissionById(Long id) {
		if (id == null) {
			return null;
		}
		Permission permission = new Permission();
		permission.setId(id);
		List<Permission> permissionList = queryPermission(permission);
		if (permissionList != null) {
			return permissionList.get(0);
		}
		return null;
	}
	
	@WriteDataSource
	public List<Permission> queryPermissionByIdList(List<Long> ids) {
		if (ids == null) {
			return null;
		}
		return permissionManager.queryPermissionListByIdSet(ids);
	}

	@WriteDataSource
	public int savePermission(Permission permission) {
		return permissionManager.savePermission(permission);
	}
	
	@WriteDataSource
	public int updatePermission(Permission permission) {
		if (permission.getId() == null) {
			return 0;
		}
		return permissionManager.updatePermission(permission);
	}
	
	@WriteDataSource
	public int updatePermissionSortIndex(Permission permission) {
		return permissionManager.updatePermissionSortIndex(permission);
	}

	@WriteDataSource
	public int deletePermission(Long id) {
		return permissionManager.deletePermission(id);
	}

}
