package com.xxyp.admin.server.service.system.mannger;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.system.Permission;

public interface PermissionManager {
	public List<Permission> queryPermissionList(Permission permission);
	
	public DataPage<Permission> queryPermissionList(DataPage<Permission> page, Permission permission);
	
	public List<Permission> queryPermissionListByIdSet(final Collection<Long> ids);
	public Map<Long,Permission> queryPermissionMapByIdSet(final Collection<Long> ids);
	
	public int savePermission(Permission permission);
	
	
	public int updatePermission(Permission permission);
	
	public int updatePermissionSortIndex(Permission permission);
	
	public int deletePermission(Long id);
}
