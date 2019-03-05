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
import com.xxyp.admin.common.service.system.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/queryRole",method = RequestMethod.POST)
	public List<Role> queryRoleList(@RequestBody Role role){
		return roleService.queryRoleList(role);
	}
	
	@RequestMapping(value="/getRoleById")
	public Role getRoleById(@RequestParam Long id) {
		return roleService.getRoleById(id);
	}
	
	@RequestMapping(value="/queryRoleList",method = RequestMethod.POST)
	public PageResult<Role> queryRoleList(@RequestBody DataPage<Role>  page){
		return roleService.queryRoleList(page);
	}
	
	@RequestMapping(value="/saveRole",method = RequestMethod.POST)
	public long saveRole(@RequestBody Role role) {
		return roleService.saveRole(role);
	}
	
	@RequestMapping(value="/updateRole",method = RequestMethod.POST)
	public int updateRole(@RequestBody Role role) {
		return roleService.updateRole(role);
	}
	
	@RequestMapping(value="/deleteRole")
	public int deleteRole(@RequestParam Long id) {
		return roleService.deleteRole(id);
	}
	
}
