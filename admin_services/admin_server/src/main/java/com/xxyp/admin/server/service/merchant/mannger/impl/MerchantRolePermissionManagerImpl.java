package com.xxyp.admin.server.service.merchant.mannger.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxyp.admin.common.model.merchant.MerchantRolePermission;
import com.xxyp.admin.common.util.MapUtil;
import com.xxyp.admin.server.dao.GenericMybatisDao;
import com.xxyp.admin.server.service.merchant.mannger.MerchantRolePermissionManager;
@Component
public class MerchantRolePermissionManagerImpl implements  MerchantRolePermissionManager{
	@Autowired
	private GenericMybatisDao adminDbDao;

	public List<MerchantRolePermission> queryRolePermissionListByRoleId(Long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", id);
		return adminDbDao.queryList("MerchantRolePermissionMapper.queryRolePermission", paramMap);
	}
	
	public List<MerchantRolePermission> queryRolePermissionList(MerchantRolePermission rolePermission) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(rolePermission);
		return adminDbDao.queryList("MerchantRolePermissionMapper.queryRolePermission", paramMap);
	}
	
	public List<Long> queryDistinctPermissionIdByRoleId(Long roleId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		return adminDbDao.queryList("MerchantRolePermissionMapper.queryDistinctPermissionId", paramMap);
	}
	
	public List<Long> queryDistinctPermissionItemIdByRoleId(Long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", id);
		return adminDbDao.queryList("MerchantRolePermissionMapper.queryDistinctPermissionItemId", paramMap);
	}
	
	public void saveRolePermission(MerchantRolePermission rolePermission) {
		adminDbDao.insertAndSetupId("MerchantRolePermissionMapper.saveRolePermission", rolePermission);
	}
	
	public int deleteRolePermission(MerchantRolePermission rolePermission) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(rolePermission);
		return adminDbDao.update("MerchantRolePermissionMapper.deleteRolePermission", paramMap);
	}

}
