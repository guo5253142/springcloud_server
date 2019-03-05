package com.xxyp.admin.common.service.system;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.system.Permission;

public interface PermissionService {

	@RequestMapping(value="/queryPermission",method = RequestMethod.POST)
	public List<Permission> queryPermission(@RequestBody Permission permission);
	
	@RequestMapping(value="/queryPermissionById")
	public Permission queryPermissionById(@RequestParam("id")  Long id);
	
	@RequestMapping(value="/queryPermissionList",method = RequestMethod.POST)
	public PageResult<Permission> queryPermissionList(@RequestBody DataPage<Permission>  page);
	
	@RequestMapping(value="/queryPermissionByIdList")
	public List<Permission> queryPermissionByIdList(@RequestParam("ids") List<Long> ids);
	
	@RequestMapping(value="/savePermission",method = RequestMethod.POST)
	public int savePermission(@RequestBody Permission permission);
	
	@RequestMapping(value="/updatePermission",method = RequestMethod.POST)
	public int updatePermission(@RequestBody Permission permission);
	
	@RequestMapping(value="/updatePermissionSortIndex",method = RequestMethod.POST)
	public int updatePermissionSortIndex(@RequestBody Permission permission);
	
	@RequestMapping(value="/deletePermission")
	public int deletePermission(@RequestParam("id") Long id);
	
	
}
