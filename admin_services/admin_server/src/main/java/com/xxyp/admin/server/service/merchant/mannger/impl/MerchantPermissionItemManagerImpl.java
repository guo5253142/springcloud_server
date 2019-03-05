package com.xxyp.admin.server.service.merchant.mannger.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxyp.admin.common.model.merchant.MerchantPermissionItem;
import com.xxyp.admin.server.dao.GenericMybatisDao;
import com.xxyp.admin.server.service.merchant.mannger.MerchantPermissionItemManager;
@Component
public class MerchantPermissionItemManagerImpl implements MerchantPermissionItemManager{
	@Autowired
	private GenericMybatisDao adminDbDao;

	public List<MerchantPermissionItem> queryPermissionItemIdList(final Collection<Long> ids){
		
		final List<MerchantPermissionItem> resultList = new ArrayList<MerchantPermissionItem>();
		if (ids == null || ids.isEmpty()) {
			return resultList;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idList", ids);
		List<MerchantPermissionItem> permissionList = adminDbDao.queryList("MerchantPermissionItemMapper.queryPermissionItemByIdSet", paramMap);
		resultList.addAll(permissionList);
		return resultList;
		
	}
	public Map<Long,MerchantPermissionItem> queryPermissionItemMapIdList(final Collection<Long> ids){
		
		final Map<Long,MerchantPermissionItem> resultMap = new HashMap<Long,MerchantPermissionItem>();
		if (ids == null || ids.isEmpty()) {
			return resultMap;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idList", ids);
		List<MerchantPermissionItem> permissionList = adminDbDao.queryList("MerchantPermissionItemMapper.queryPermissionItemByIdSet", paramMap);
		for(MerchantPermissionItem per:permissionList){
			resultMap.put(per.getId(), per);
		}
		return resultMap;
		
	}
	
	
	public List<MerchantPermissionItem> queryPermissionItemList(MerchantPermissionItem permissionItem) {
		Map<String, Object> paramMap = conditionMap(permissionItem);
		return adminDbDao.queryList("MerchantPermissionItemMapper.queryPermissionItem", paramMap);
	}
	
	public int savePermissionItem(MerchantPermissionItem permissionItem) {
		if (permissionItem.getPermissionId() == null) {
			return 0;
		}
		return adminDbDao.insertAndSetupId("MerchantPermissionItemMapper.savePermissionItem", permissionItem);
	}
	
	
	public int updatePermissionItem(MerchantPermissionItem permissionItem) {
		return adminDbDao.updateByObj("MerchantPermissionItemMapper.updatePermissionItem", permissionItem);
	}
	
	private Map<String, Object> conditionMap(MerchantPermissionItem permissionItem) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (permissionItem != null) {
			if (StringUtils.isNotBlank(permissionItem.getName())) {
				paramMap.put("name", permissionItem.getName());
			}
			if (permissionItem.getId() != null) {
				paramMap.put("id", permissionItem.getId());
			}
			if (StringUtils.isNotBlank(permissionItem.getMethodValue1())) {
				paramMap.put("methodValue1", permissionItem.getMethodValue1());
			}
			if (StringUtils.isNotBlank(permissionItem.getMethodValue2())) {
				paramMap.put("methodValue2", permissionItem.getMethodValue2());
			}
			if (permissionItem.getPermissionId() != null) {
				paramMap.put("permissionId", permissionItem.getPermissionId());
			}
		}
		return paramMap;
	}
	
	public int delPermissionItem(Long id) {
		if (id == null) {
			return 0;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return adminDbDao.update("MerchantPermissionItemMapper.deletePermissionItem", paramMap);
	}
	@Override
	public int delPermissionItemByIds(List<String> ids) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		return adminDbDao.update("MerchantPermissionItemMapper.delPermissionItemByIds", paramMap);
	}
}
