package com.xxyp.admin.server.controller.system;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.system.Role;
import com.xxyp.admin.common.model.system.RolePermission;
import com.xxyp.admin.common.service.system.RolePermissionService;
import com.xxyp.admin.common.service.system.RoleService;

@RestController
@RequestMapping("/rolePermission")
public class RolePermissionController {

	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@RequestMapping(value="/queryRolePermissionListByRoleId")
	public List<RolePermission> queryRolePermissionListByRoleId(@RequestParam Long roleId){
		return rolePermissionService.queryRolePermissionListByRoleId(roleId);
	}
	
	@RequestMapping(value="/queryDistinctPermissionIdByRoleId")
	public List<Long> queryDistinctPermissionIdByRoleId(@RequestParam Long roleId){
		return rolePermissionService.queryDistinctPermissionIdByRoleId(roleId);
	}
	
	@RequestMapping(value="/queryRolePermissionList",method = RequestMethod.POST)
	public List<RolePermission> queryRolePermissionList(@RequestBody RolePermission rolePermission){
		return rolePermissionService.queryRolePermissionList(rolePermission);
	}
	
	@RequestMapping(value="/saveRolePermission",method = RequestMethod.POST)
	public void saveRolePermission(@RequestBody RolePermission rolePermission) {
		rolePermissionService.saveRolePermission(rolePermission);
	}
	
	@RequestMapping(value="/deleteRolePermission",method = RequestMethod.POST)
	public int deleteRolePermission(@RequestBody RolePermission rolePermission) {
		return rolePermissionService.deleteRolePermission(rolePermission);
	}
	
}
