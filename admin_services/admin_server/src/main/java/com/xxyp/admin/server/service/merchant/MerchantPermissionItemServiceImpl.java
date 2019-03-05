package com.xxyp.admin.server.service.merchant;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xxyp.admin.common.model.merchant.MerchantPermissionItem;
import com.xxyp.admin.common.model.merchant.MerchantRolePermission;
import com.xxyp.admin.common.service.merchant.MerchantPermissionItemService;
import com.xxyp.admin.server.annotation.WriteDataSource;
import com.xxyp.admin.server.service.merchant.mannger.MerchantPermissionItemManager;
import com.xxyp.admin.server.service.merchant.mannger.MerchantRolePermissionManager;
@Service
public class MerchantPermissionItemServiceImpl implements MerchantPermissionItemService {
	@Autowired
	public MerchantPermissionItemManager permissionItemManager;
	@Autowired
	public MerchantRolePermissionManager rolePermissionManager;

	@WriteDataSource
	public List<MerchantPermissionItem> queryPermissionItemList(
			MerchantPermissionItem permissionItem) {
		return permissionItemManager.queryPermissionItemList(permissionItem);
	}

	@WriteDataSource
	public int savePermissionItem(MerchantPermissionItem permissionItem) {
		return permissionItemManager.savePermissionItem(permissionItem);
	}

	@WriteDataSource
	public int updatePermissionItem(MerchantPermissionItem permissionItem) {
		return permissionItemManager.updatePermissionItem(permissionItem);
	}

	@WriteDataSource
	public MerchantPermissionItem getPermissionItemById(Long id) {
		if (id == null) {
			return null;
		}
		MerchantPermissionItem item = new MerchantPermissionItem();
		item.setId(id);
		List<MerchantPermissionItem> resultList = queryPermissionItemList(item);
		if (resultList != null && resultList.size() > 0) {
			return resultList.get(0);
		}
		return null;
	}

	@WriteDataSource
	@Transactional
	public int delPermissionItemById(Long id) {
		MerchantRolePermission rolePermission=new MerchantRolePermission();
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
