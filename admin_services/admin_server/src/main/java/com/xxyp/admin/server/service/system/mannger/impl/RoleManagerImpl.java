package com.xxyp.admin.server.service.system.mannger.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.system.Role;
import com.xxyp.admin.server.dao.GenericMybatisDao;
import com.xxyp.admin.server.service.system.mannger.RoleManager;
@Component
public class RoleManagerImpl implements RoleManager{
	@Autowired
	private GenericMybatisDao adminDbDao;

	public List<Role> queryRoleList(Role role) {
		Map<String, Object> paramMap = conditionMap(role);
		return adminDbDao.queryList("RoleMapper.getRole", paramMap);
	}
	
	public DataPage<Role> queryRoleList(DataPage<Role> page) {
		Map<String, Object> paramMap = conditionMap(page.getDataObj());
		return adminDbDao.queryPage("RoleMapper.countRoleForPage", "RoleMapper.queryRoleForPage", paramMap, page);
	}
	
	public long saveRole(Role role) {
		adminDbDao.insertAndSetupId("RoleMapper.saveRole", role);
		return role.getId();
	}
	
	public int updateRole(Role role) {
		return adminDbDao.updateByObj("RoleMapper.updateRole", role);
	}
	
	public int deleteRole(Long id) {
		if (id == null) {
			return 0;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return adminDbDao.update("RoleMapper.deleteRole", paramMap);
	}
	
	private Map<String, Object> conditionMap(Role role) {
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
