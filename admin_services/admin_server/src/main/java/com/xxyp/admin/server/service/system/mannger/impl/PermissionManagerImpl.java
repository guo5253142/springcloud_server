package com.xxyp.admin.server.service.system.mannger.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.system.Permission;
import com.xxyp.admin.server.dao.GenericMybatisDao;
import com.xxyp.admin.server.service.system.mannger.PermissionManager;
@Component
public class PermissionManagerImpl implements PermissionManager{
	@Autowired
	private GenericMybatisDao adminDbDao;

	public List<Permission> queryPermissionList(Permission permission) {
		Map<String, Object> paramMap = conditionMap(permission);
		return adminDbDao.queryList("PermissionMapper.getPermission", paramMap);
	}
	
	public DataPage<Permission> queryPermissionList(DataPage<Permission> page, Permission permission) {
		Map<String, Object> paramMap = conditionMap(permission);
		return adminDbDao.queryPage("PermissionMapper.countPermissionForPage",
				"PermissionMapper.queryPermissionForPage", paramMap, page);
	}
	
	public List<Permission> queryPermissionListByIdSet(final Collection<Long> ids){
		final List<Permission> resultList = new ArrayList<Permission>();
		if (ids == null || ids.isEmpty()) {
			return resultList;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idList", ids);
		List<Permission> permissionList = adminDbDao.queryList("PermissionMapper.queryPermissionByIdSet", paramMap);
		resultList.addAll(permissionList);
		return resultList;
	}
	public Map<Long,Permission> queryPermissionMapByIdSet(final Collection<Long> ids){
		final  Map<Long,Permission> resultMap = new  HashMap<Long,Permission>();
		
		if (ids == null || ids.isEmpty()) {
			return resultMap;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//String idList = StringUtils.join(collection, ",");
		paramMap.put("idList", ids);
		List<Permission> permissionList = adminDbDao.queryList("PermissionMapper.queryPermissionByIdSet", paramMap);
		for(Permission per : permissionList){
			resultMap.put(per.getId(), per);
		}
		return resultMap;
	}
	
	public int savePermission(Permission permission) {
		/*Long  sortIndex = adminDbDao.generateSequence("PermissionMapper.getMaxOrderIndex");
		if (sortIndex != null) {
			permission.setOrderIndex(sortIndex.intValue() + 1);
		}*/
		return adminDbDao.insertAndSetupId("PermissionMapper.savePermission", permission);
	}
	
	
	public int updatePermission(Permission permission) {
		return adminDbDao.updateByObj("PermissionMapper.updatePermission", permission);
	}
	
	public int updatePermissionSortIndex(Permission permission) {
		/*if (permission.getOrderIndex().intValue() == 0) {
			Long  sortIndex = adminDbDao.generateSequence("PermissionMapper.getMaxOrderIndex");
			permission.setOrderIndex(sortIndex.intValue() + 1);
		}*/
		return adminDbDao.updateByObj("PermissionMapper.updatePermissionOrderIndex", permission);
	}
	
	public int deletePermission(Long id) {
		if (id == null) {
			return 0;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return adminDbDao.update("PermissionMapper.deletePermissionById", paramMap);
	}
	
	private Map<String, Object> conditionMap(Permission permission) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (permission != null) {
			if (StringUtils.isNotBlank(permission.getName())) {
				paramMap.put("name", permission.getName());
			}
			if (permission.getId() != null) {
				paramMap.put("id", permission.getId());
			}
			if (StringUtils.isNotBlank(permission.getMenuUrl())) {
				paramMap.put("menuUrl", permission.getMenuUrl());
			}
			if (permission.getModuleId() != null) {
				paramMap.put("moduleId", permission.getModuleId());
			}
		}
		return paramMap;
	}
}
