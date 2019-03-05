package com.xxyp.admin.server.service.system;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.system.Role;
import com.xxyp.admin.common.service.system.RoleService;
import com.xxyp.admin.server.annotation.WriteDataSource;
import com.xxyp.admin.server.service.system.mannger.RoleManager;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	public RoleManager roleManager;

	@WriteDataSource
	public List<Role> queryRoleList(Role role) {
		return roleManager.queryRoleList(role);
	}

	@WriteDataSource
	public PageResult<Role> queryRoleList(DataPage<Role> page) {
		page = roleManager.queryRoleList(page);
		PageResult<Role> result = new PageResult<Role>();
		result.setPage(page);
		return result;
	}

	@WriteDataSource
	public long saveRole(Role role) {
		return roleManager.saveRole(role);
	}
	
	@WriteDataSource
	public int updateRole(Role role) {
		return roleManager.updateRole(role);
	}
	
	@WriteDataSource
	public int deleteRole(Long id) {
		return roleManager.deleteRole(id);
	}

	@WriteDataSource
	public Role getRoleById(Long id) {
		if (id == null) {
			return null;
		}
		Role role = new Role();
		role.setId(id);
		List<Role> list = queryRoleList(role);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	
}
