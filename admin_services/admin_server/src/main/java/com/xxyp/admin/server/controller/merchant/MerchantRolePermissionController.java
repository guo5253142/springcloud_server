package com.xxyp.admin.server.controller.merchant;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xxyp.admin.common.model.merchant.MerchantRolePermission;
import com.xxyp.admin.common.service.merchant.MerchantRolePermissionService;

@RestController
@RequestMapping("/merchantRolePermission")
public class MerchantRolePermissionController {

	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MerchantRolePermissionService rolePermissionService;
	
	@RequestMapping(value="/queryRolePermissionListByRoleId")
	public List<MerchantRolePermission> queryRolePermissionListByRoleId(@RequestParam Long roleId){
		return rolePermissionService.queryRolePermissionListByRoleId(roleId);
	}
	
	@RequestMapping(value="/queryDistinctPermissionIdByRoleId")
	public List<Long> queryDistinctPermissionIdByRoleId(@RequestParam Long roleId){
		return rolePermissionService.queryDistinctPermissionIdByRoleId(roleId);
	}
	
	@RequestMapping(value="/queryRolePermissionList",method = RequestMethod.POST)
	public List<MerchantRolePermission> queryRolePermissionList(@RequestBody MerchantRolePermission rolePermission){
		return rolePermissionService.queryRolePermissionList(rolePermission);
	}
	
	@RequestMapping(value="/saveRolePermission",method = RequestMethod.POST)
	public void saveRolePermission(@RequestBody MerchantRolePermission rolePermission) {
		rolePermissionService.saveRolePermission(rolePermission);
	}
	
	@RequestMapping(value="/deleteRolePermission",method = RequestMethod.POST)
	public int deleteRolePermission(@RequestBody MerchantRolePermission rolePermission) {
		return rolePermissionService.deleteRolePermission(rolePermission);
	}
	
}
