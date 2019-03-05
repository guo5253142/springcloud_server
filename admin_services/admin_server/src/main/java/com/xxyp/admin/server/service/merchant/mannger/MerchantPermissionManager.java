package com.xxyp.admin.server.service.merchant.mannger;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.merchant.MerchantPermission;

public interface MerchantPermissionManager {
	public List<MerchantPermission> queryPermissionList(MerchantPermission permission);
	
	public DataPage<MerchantPermission> queryPermissionList(DataPage<MerchantPermission> page, MerchantPermission permission);
	
	public List<MerchantPermission> queryPermissionListByIdSet(final Collection<Long> ids);
	public Map<Long,MerchantPermission> queryPermissionMapByIdSet(final Collection<Long> ids);
	
	public int savePermission(MerchantPermission permission);
	
	
	public int updatePermission(MerchantPermission permission);
	
	public int updatePermissionSortIndex(MerchantPermission permission);
	
	public int deletePermission(Long id);
}
