package com.xxyp.admin.common.service.system;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xxyp.admin.common.model.system.RolePermission;

public interface RolePermissionService {

	@RequestMapping(value="/queryRolePermissionListByRoleId")
	public List<RolePermission> queryRolePermissionListByRoleId(@RequestParam("roleId") Long roleId);
	
	@RequestMapping(value="/queryDistinctPermissionIdByRoleId")
	public List<Long> queryDistinctPermissionIdByRoleId(@RequestParam("roleId") Long roleId);
	
	@RequestMapping(value="/queryRolePermissionList",method = RequestMethod.POST)
	public List<RolePermission> queryRolePermissionList(@RequestBody RolePermission rolePermission);
	
	@RequestMapping(value="/saveRolePermission",method = RequestMethod.POST)
	public void saveRolePermission(@RequestBody RolePermission rolePermission);
	
	@RequestMapping(value="/deleteRolePermission",method = RequestMethod.POST)
	public int deleteRolePermission(@RequestBody RolePermission rolePermission);
}
