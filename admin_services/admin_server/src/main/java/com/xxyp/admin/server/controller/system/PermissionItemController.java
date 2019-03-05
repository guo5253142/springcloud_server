package com.xxyp.admin.server.controller.system;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xxyp.admin.common.model.system.PermissionItem;
import com.xxyp.admin.common.service.system.PermissionItemService;


@RestController
@RequestMapping("/permissionItem")
public class PermissionItemController{
	
	@Autowired
	private PermissionItemService permissionItemService;
	
	@RequestMapping(value="/queryPermissionItemList",method = RequestMethod.POST)
	public List<PermissionItem> queryPermissionItemList(@RequestBody PermissionItem permissionItem){
		return permissionItemService.queryPermissionItemList(permissionItem);
	}
	
	@RequestMapping(value="/getPermissionItemById")
	public PermissionItem getPermissionItemById(@RequestParam Long id) {
		return permissionItemService.getPermissionItemById(id);
	}
	
	@RequestMapping(value="/savePermissionItem",method = RequestMethod.POST)
	public int savePermissionItem(@RequestBody PermissionItem permissionItem) {
		return permissionItemService.savePermissionItem(permissionItem);
	}
	
	@RequestMapping(value="/updatePermissionItem",method = RequestMethod.POST)
	public int updatePermissionItem(@RequestBody PermissionItem permissionItem) {
		return permissionItemService.updatePermissionItem(permissionItem);
	}
	
	@RequestMapping(value="/delPermissionItemById")
	public int delPermissionItemById(@RequestParam Long id) {
		return permissionItemService.delPermissionItemById(id);
	}
	
	@RequestMapping(value="/delPermissionItemByIds")
	public int delPermissionItemByIds(@RequestParam List<String> ids) {
		return permissionItemService.delPermissionItemByIds(ids);
	}
}
