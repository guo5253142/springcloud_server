package com.xxyp.admin.server.service.system;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xxyp.admin.common.model.system.PermissionItem;
import com.xxyp.admin.common.model.system.RolePermission;
import com.xxyp.admin.common.service.system.PermissionItemService;
import com.xxyp.admin.server.annotation.WriteDataSource;
import com.xxyp.admin.server.service.system.mannger.PermissionItemManager;
import com.xxyp.admin.server.service.system.mannger.RolePermissionManager;
@Service
public class PermissionItemServiceImpl implements PermissionItemService {
	@Autowired
	public PermissionItemManager permissionItemManager;
	@Autowired
	public RolePermissionManager rolePermissionManager;

	@WriteDataSource
	public List<PermissionItem> queryPermissionItemList(
			PermissionItem permissionItem) {
		return permissionItemManager.queryPermissionItemList(permissionItem);
	}

	@WriteDataSource
	public int savePermissionItem(PermissionItem permissionItem) {
		return permissionItemManager.savePermissionItem(permissionItem);
	}

	@WriteDataSource
	public int updatePermissionItem(PermissionItem permissionItem) {
		return permissionItemManager.updatePermissionItem(permissionItem);
	}

	@WriteDataSource
	public PermissionItem getPermissionItemById(Long id) {
		if (id == null) {
			return null;
		}
		PermissionItem item = new PermissionItem();
		item.setId(id);
		List<PermissionItem> resultList = queryPermissionItemList(item);
		if (resultList != null && resultList.size() > 0) {
			return resultList.get(0);
		}
		return null;
	}

	@WriteDataSource
	@Transactional
	public int delPermissionItemById(Long id) {
		RolePermission rolePermission=new RolePermission();
		rolePermission.setPermissionItemId(id);
		rolePermissionManager.deleteRolePermission(rolePermission);
		return permissionItemManager.delPermissionItem(id);
	}

	@WriteDataSource
	public int delPermissionItemByIds(List<String> ids) {
		// TODO Auto-generated method stub
		return permissionItemManager.delPermissionItemByIds(ids);
	}



	
}
