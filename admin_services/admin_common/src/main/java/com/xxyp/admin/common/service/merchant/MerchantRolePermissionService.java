package com.xxyp.admin.common.service.merchant;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xxyp.admin.common.model.merchant.MerchantRolePermission;

public interface MerchantRolePermissionService {

	@RequestMapping(value="/queryRolePermissionListByRoleId")
	public List<MerchantRolePermission> queryRolePermissionListByRoleId(@RequestParam("roleId") Long roleId);
	
	@RequestMapping(value="/queryDistinctPermissionIdByRoleId")
	public List<Long> queryDistinctPermissionIdByRoleId(@RequestParam("roleId") Long roleId);
	
	@RequestMapping(value="/queryRolePermissionList",method = RequestMethod.POST)
	public List<MerchantRolePermission> queryRolePermissionList(@RequestBody MerchantRolePermission rolePermission);
	
	@RequestMapping(value="/saveRolePermission",method = RequestMethod.POST)
	public void saveRolePermission(@RequestBody MerchantRolePermission rolePermission);
	
	@RequestMapping(value="/deleteRolePermission",method = RequestMethod.POST)
	public int deleteRolePermission(@RequestBody MerchantRolePermission rolePermission);
}
