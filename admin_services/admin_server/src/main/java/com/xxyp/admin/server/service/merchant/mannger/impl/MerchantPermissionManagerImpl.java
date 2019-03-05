package com.xxyp.admin.server.service.merchant.mannger.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.merchant.MerchantPermission;
import com.xxyp.admin.server.dao.GenericMybatisDao;
import com.xxyp.admin.server.service.merchant.mannger.MerchantPermissionManager;
@Component
public class MerchantPermissionManagerImpl implements MerchantPermissionManager{
	@Autowired
	private GenericMybatisDao adminDbDao;

	public List<MerchantPermission> queryPermissionList(MerchantPermission permission) {
		Map<String, Object> paramMap = conditionMap(permission);
		return adminDbDao.queryList("MerchantPermissionMapper.getPermission", paramMap);
	}
	
	public DataPage<MerchantPermission> queryPermissionList(DataPage<MerchantPermission> page, MerchantPermission permission) {
		Map<String, Object> paramMap = conditionMap(permission);
		return adminDbDao.queryPage("MerchantPermissionMapper.countPermissionForPage",
				"MerchantPermissionMapper.queryPermissionForPage", paramMap, page);
	}
	
	public List<MerchantPermission> queryPermissionListByIdSet(final Collection<Long> ids){
		final List<MerchantPermission> resultList = new ArrayList<MerchantPermission>();
		if (ids == null || ids.isEmpty()) {
			return resultList;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idList", ids);
		List<MerchantPermission> permissionList = adminDbDao.queryList("MerchantPermissionMapper.queryPermissionByIdSet", paramMap);
		resultList.addAll(permissionList);
		return resultList;
	}
	public Map<Long,MerchantPermission> queryPermissionMapByIdSet(final Collection<Long> ids){
		final  Map<Long,MerchantPermission> resultMap = new  HashMap<Long,MerchantPermission>();
		
		if (ids == null || ids.isEmpty()) {
			return resultMap;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//String idList = StringUtils.join(collection, ",");
		paramMap.put("idList", ids);
		List<MerchantPermission> permissionList = adminDbDao.queryList("MerchantPermissionMapper.queryPermissionByIdSet", paramMap);
		for(MerchantPermission per : permissionList){
			resultMap.put(per.getId(), per);
		}
		return resultMap;
	}
	
	public int savePermission(MerchantPermission permission) {
		/*Long  sortIndex = adminDbDao.generateSequence("MerchantPermissionMapper.getMaxOrderIndex");
		if (sortIndex != null) {
			permission.setOrderIndex(sortIndex.intValue() + 1);
		}*/
		return adminDbDao.insertAndSetupId("MerchantPermissionMapper.savePermission", permission);
	}
	
	
	public int updatePermission(MerchantPermission permission) {
		return adminDbDao.updateByObj("MerchantPermissionMapper.updatePermission", permission);
	}
	
	public int updatePermissionSortIndex(MerchantPermission permission) {
		/*if (permission.getOrderIndex().intValue() == 0) {
			Long  sortIndex = adminDbDao.generateSequence("MerchantPermissionMapper.getMaxOrderIndex");
			permission.setOrderIndex(sortIndex.intValue() + 1);
		}*/
		return adminDbDao.updateByObj("MerchantPermissionMapper.updatePermissionOrderIndex", permission);
	}
	
	public int deletePermission(Long id) {
		if (id == null) {
			return 0;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return adminDbDao.update("MerchantPermissionMapper.deletePermissionById", paramMap);
	}
	
	private Map<String, Object> conditionMap(MerchantPermission permission) {
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
