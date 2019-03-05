package com.xxyp.admin.server.service.merchant.mannger;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.xxyp.admin.common.model.merchant.MerchantPermissionItem;

public interface MerchantPermissionItemManager {
public List<MerchantPermissionItem> queryPermissionItemIdList(final Collection<Long> ids);
	public Map<Long,MerchantPermissionItem> queryPermissionItemMapIdList(final Collection<Long> ids);
	
	
	public List<MerchantPermissionItem> queryPermissionItemList(MerchantPermissionItem permissionItem);
	
	public int savePermissionItem(MerchantPermissionItem permissionItem);
	
	
	public int updatePermissionItem(MerchantPermissionItem permissionItem);
	
	
	public int delPermissionItem(Long id);
	
	public int delPermissionItemByIds(List<String> ids);
}
