package com.xxyp.admin.common.service.merchant;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xxyp.admin.common.model.merchant.MerchantPermissionItem;

public interface MerchantPermissionItemService {

	@RequestMapping(value="/queryPermissionItemList",method = RequestMethod.POST)
	public List<MerchantPermissionItem> queryPermissionItemList(@RequestBody MerchantPermissionItem permissionItem);
	
	@RequestMapping(value="/getPermissionItemById")
	public MerchantPermissionItem getPermissionItemById(@RequestParam("id") Long id);
	
	@RequestMapping(value="/savePermissionItem",method = RequestMethod.POST)
	public int savePermissionItem(@RequestBody MerchantPermissionItem permissionItem);
	
	@RequestMapping(value="/updatePermissionItem",method = RequestMethod.POST)
	public int updatePermissionItem(@RequestBody MerchantPermissionItem permissionItem);
	
	@RequestMapping(value="/delPermissionItemById")
	public int delPermissionItemById(@RequestParam("id") Long id);
	
	@RequestMapping(value="/delPermissionItemByIds")
	public int delPermissionItemByIds(@RequestParam("ids") List<String> ids);
	
}
