package com.xxyp.admin.server.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.system.Permission;
import com.xxyp.admin.common.service.system.PermissionService;


@RestController
@RequestMapping("/permission")
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(value="/queryPermission",method = RequestMethod.POST)
	public List<Permission> queryPermission(@RequestBody Permission permission){
		return permissionService.queryPermission(permission);
	}
	
	@RequestMapping(value="/queryPermissionById")
	public Permission queryPermissionById(@RequestParam  Long id) {
		return permissionService.queryPermissionById(id);
	}
	
	@RequestMapping(value="/queryPermissionList",method = RequestMethod.POST)
	public PageResult<Permission> queryPermissionList(@RequestBody DataPage<Permission>  page){
		return permissionService.queryPermissionList(page);
	}
	
	@RequestMapping(value="/queryPermissionByIdList")
	public List<Permission> queryPermissionByIdList(@RequestParam List<Long> ids){
		return permissionService.queryPermissionByIdList(ids);
	}
	
	@RequestMapping(value="/savePermission",method = RequestMethod.POST)
	public int savePermission(@RequestBody Permission permission) {
		return permissionService.savePermission(permission);
	}
	
	@RequestMapping(value="/updatePermission",method = RequestMethod.POST)
	public int updatePermission(@RequestBody Permission permission) {
		return permissionService.updatePermission(permission);
	}
	
	@RequestMapping(value="/updatePermissionSortIndex",method = RequestMethod.POST)
	public int updatePermissionSortIndex(@RequestBody Permission permission) {
		return permissionService.updatePermissionSortIndex(permission);
	}
	
	@RequestMapping(value="/deletePermission")
	public int deletePermission(@RequestParam Long id) {
		return permissionService.deletePermission(id);
	}
	
}
