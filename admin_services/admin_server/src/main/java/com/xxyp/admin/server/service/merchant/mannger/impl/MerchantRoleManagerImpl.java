package com.xxyp.admin.server.service.merchant.mannger.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.merchant.MerchantRole;
import com.xxyp.admin.server.dao.GenericMybatisDao;
import com.xxyp.admin.server.service.merchant.mannger.MerchantRoleManager;
@Component
public class MerchantRoleManagerImpl implements MerchantRoleManager{
	@Autowired
	private GenericMybatisDao adminDbDao;

	public List<MerchantRole> queryRoleList(MerchantRole role) {
		Map<String, Object> paramMap = conditionMap(role);
		return adminDbDao.queryList("MerchantRoleMapper.getRole", paramMap);
	}
	
	public DataPage<MerchantRole> queryRoleList(DataPage<MerchantRole> page) {
		Map<String, Object> paramMap = conditionMap(page.getDataObj());
		return adminDbDao.queryPage("MerchantRoleMapper.countRoleForPage", "MerchantRoleMapper.queryRoleForPage", paramMap, page);
	}
	
	public long saveRole(MerchantRole role) {
		adminDbDao.insertAndSetupId("MerchantRoleMapper.saveRole", role);
		return role.getId();
	}
	
	public int updateRole(MerchantRole role) {
		return adminDbDao.updateByObj("MerchantRoleMapper.updateRole", role);
	}
	
	public int deleteRole(Long id) {
		if (id == null) {
			return 0;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return adminDbDao.update("MerchantRoleMapper.deleteRole", paramMap);
	}
	
	private Map<String, Object> conditionMap(MerchantRole role) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (role != null) {
			if (StringUtils.isNotBlank(role.getName())) {
				paramMap.put("name", role.getName());
			}
			if (role.getId() != null) {
				paramMap.put("id", role.getId());
			}
			if (StringUtils.isNotBlank(role.getRemark())) {
				paramMap.put("remark", role.getRemark());
			}
		}
		return paramMap;
	}
}
