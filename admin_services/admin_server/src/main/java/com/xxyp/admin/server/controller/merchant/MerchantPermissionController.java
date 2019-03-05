package com.xxyp.admin.server.controller.merchant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.merchant.MerchantPermission;
import com.xxyp.admin.common.service.merchant.MerchantPermissionService;


@RestController
@RequestMapping("/merchantPermission")
public class MerchantPermissionController {
	
	@Autowired
	private MerchantPermissionService permissionService;
	
	@RequestMapping(value="/queryPermission",method = RequestMethod.POST)
	public List<MerchantPermission> queryPermission(@RequestBody MerchantPermission permission){
		return permissionService.queryPermission(permission);
	}
	
	@RequestMapping(value="/queryPermissionById")
	public MerchantPermission queryPermissionById(@RequestParam  Long id) {
		return permissionService.queryPermissionById(id);
	}
	
	@RequestMapping(value="/queryPermissionList",method = RequestMethod.POST)
	public PageResult<MerchantPermission> queryPermissionList(@RequestBody DataPage<MerchantPermission>  page){
		return permissionService.queryPermissionList(page);
	}
	
	@RequestMapping(value="/queryPermissionByIdList")
	public List<MerchantPermission> queryPermissionByIdList(@RequestParam List<Long> ids){
		return permissionService.queryPermissionByIdList(ids);
	}
	
	@RequestMapping(value="/savePermission",method = RequestMethod.POST)
	public int savePermission(@RequestBody MerchantPermission permission) {
		return permissionService.savePermission(permission);
	}
	
	@RequestMapping(value="/updatePermission",method = RequestMethod.POST)
	public int updatePermission(@RequestBody MerchantPermission permission) {
		return permissionService.updatePermission(permission);
	}
	
	@RequestMapping(value="/updatePermissionSortIndex",method = RequestMethod.POST)
	public int updatePermissionSortIndex(@RequestBody MerchantPermission permission) {
		return permissionService.updatePermissionSortIndex(permission);
	}
	
	@RequestMapping(value="/deletePermission")
	public int deletePermission(@RequestParam Long id) {
		return permissionService.deletePermission(id);
	}
	
}
