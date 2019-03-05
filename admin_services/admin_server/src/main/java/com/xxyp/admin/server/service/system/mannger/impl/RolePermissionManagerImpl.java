package com.xxyp.admin.server.service.system.mannger.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxyp.admin.common.model.system.RolePermission;
import com.xxyp.admin.common.util.MapUtil;
import com.xxyp.admin.server.dao.GenericMybatisDao;
import com.xxyp.admin.server.service.system.mannger.RolePermissionManager;
@Component
public class RolePermissionManagerImpl implements  RolePermissionManager{
	@Autowired
	private GenericMybatisDao adminDbDao;

	public List<RolePermission> queryRolePermissionListByRoleId(Long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", id);
		return adminDbDao.queryList("RolePermissionMapper.queryRolePermission", paramMap);
	}
	
	public List<RolePermission> queryRolePermissionList(RolePermission rolePermission) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(rolePermission);
		return adminDbDao.queryList("RolePermissionMapper.queryRolePermission", paramMap);
	}
	
	public List<Long> queryDistinctPermissionIdByRoleId(Long roleId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		return adminDbDao.queryList("RolePermissionMapper.queryDistinctPermissionId", paramMap);
	}
	
	public List<Long> queryDistinctPermissionItemIdByRoleId(Long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", id);
		return adminDbDao.queryList("RolePermissionMapper.queryDistinctPermissionItemId", paramMap);
	}
	
	public void saveRolePermission(RolePermission rolePermission) {
		adminDbDao.insertAndSetupId("RolePermissionMapper.saveRolePermission", rolePermission);
	}
	
	public int deleteRolePermission(RolePermission rolePermission) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(rolePermission);
		return adminDbDao.update("RolePermissionMapper.deleteRolePermission", paramMap);
	}

}
