package com.xxyp.admin.server.service.merchant;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.merchant.MerchantPermission;
import com.xxyp.admin.common.service.merchant.MerchantPermissionService;
import com.xxyp.admin.server.annotation.WriteDataSource;
import com.xxyp.admin.server.service.merchant.mannger.MerchantPermissionManager;
@Service
public class MerchantPermissionServiceImpl implements MerchantPermissionService {
	@Autowired
	public MerchantPermissionManager permissionManager;

	@WriteDataSource
	public List<MerchantPermission> queryPermission(MerchantPermission permission) {
		return permissionManager.queryPermissionList(permission);
	}

	@WriteDataSource
	public PageResult<MerchantPermission> queryPermissionList(DataPage<MerchantPermission> page) {
		page = permissionManager.queryPermissionList(page, page.getDataObj());
		PageResult<MerchantPermission> result = new PageResult<MerchantPermission>();
		result.setPage(page);
		return result;
	}

	@WriteDataSource
	public MerchantPermission queryPermissionById(Long id) {
		if (id == null) {
			return null;
		}
		MerchantPermission permission = new MerchantPermission();
		permission.setId(id);
		List<MerchantPermission> permissionList = queryPermission(permission);
		if (permissionList != null) {
			return permissionList.get(0);
		}
		return null;
	}
	
	@WriteDataSource
	public List<MerchantPermission> queryPermissionByIdList(List<Long> ids) {
		if (ids == null) {
			return null;
		}
		return permissionManager.queryPermissionListByIdSet(ids);
	}

	@WriteDataSource
	public int savePermission(MerchantPermission permission) {
		return permissionManager.savePermission(permission);
	}
	
	@WriteDataSource
	public int updatePermission(MerchantPermission permission) {
		if (permission.getId() == null) {
			return 0;
		}
		return permissionManager.updatePermission(permission);
	}
	
	@WriteDataSource
	public int updatePermissionSortIndex(MerchantPermission permission) {
		return permissionManager.updatePermissionSortIndex(permission);
	}

	@WriteDataSource
	public int deletePermission(Long id) {
		return permissionManager.deletePermission(id);
	}

}
