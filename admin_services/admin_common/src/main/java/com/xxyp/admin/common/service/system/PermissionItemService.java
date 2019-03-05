package com.xxyp.admin.common.service.system;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xxyp.admin.common.model.system.PermissionItem;

public interface PermissionItemService {

	@RequestMapping(value="/queryPermissionItemList",method = RequestMethod.POST)
	public List<PermissionItem> queryPermissionItemList(@RequestBody PermissionItem permissionItem);
	
	@RequestMapping(value="/getPermissionItemById")
	public PermissionItem getPermissionItemById(@RequestParam("id") Long id);
	
	@RequestMapping(value="/savePermissionItem",method = RequestMethod.POST)
	public int savePermissionItem(@RequestBody PermissionItem permissionItem);
	
	@RequestMapping(value="/updatePermissionItem",method = RequestMethod.POST)
	public int updatePermissionItem(@RequestBody PermissionItem permissionItem);
	
	@RequestMapping(value="/delPermissionItemById")
	public int delPermissionItemById(@RequestParam("id") Long id);
	
	@RequestMapping(value="/delPermissionItemByIds")
	public int delPermissionItemByIds(@RequestParam("ids") List<String> ids);
	
}
