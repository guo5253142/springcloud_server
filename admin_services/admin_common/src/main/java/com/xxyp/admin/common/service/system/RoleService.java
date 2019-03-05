package com.xxyp.admin.common.service.system;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.system.Role;

public interface RoleService{
	
	@RequestMapping(value="/queryRole",method = RequestMethod.POST)
	public List<Role> queryRoleList(@RequestBody Role role);
	
	@RequestMapping(value="/getRoleById")
	public Role getRoleById(@RequestParam("id") Long id);
	
	@RequestMapping(value="/queryRoleList",method = RequestMethod.POST)
	public PageResult<Role> queryRoleList(@RequestBody DataPage<Role>  page);
	
	@RequestMapping(value="/saveRole",method = RequestMethod.POST)
	public long saveRole(@RequestBody Role role);
	
	@RequestMapping(value="/updateRole",method = RequestMethod.POST)
	public int updateRole(@RequestBody Role role);
	
	@RequestMapping(value="/deleteRole")
	public int deleteRole(@RequestParam("id") Long id);
	
}
