package com.xxyp.admin.server.service.system.mannger;

import java.util.List;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.system.Role;

public interface RoleManager {
	public List<Role> queryRoleList(Role role);
	
	public DataPage<Role> queryRoleList(DataPage<Role> page);
	public long saveRole(Role role);
	
	public int updateRole(Role role);
	
	public int deleteRole(Long id);
	

}
