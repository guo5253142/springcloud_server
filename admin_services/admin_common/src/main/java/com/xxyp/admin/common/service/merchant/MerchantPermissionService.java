package com.xxyp.admin.common.service.merchant;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.merchant.MerchantPermission;

public interface MerchantPermissionService {

	@RequestMapping(value="/queryPermission",method = RequestMethod.POST)
	public List<MerchantPermission> queryPermission(@RequestBody MerchantPermission permission);
	
	@RequestMapping(value="/queryPermissionById")
	public MerchantPermission queryPermissionById(@RequestParam("id")  Long id);
	
	@RequestMapping(value="/queryPermissionList",method = RequestMethod.POST)
	public PageResult<MerchantPermission> queryPermissionList(@RequestBody DataPage<MerchantPermission>  page);
	
	@RequestMapping(value="/queryPermissionByIdList")
	public List<MerchantPermission> queryPermissionByIdList(@RequestParam("ids") List<Long> ids);
	
	@RequestMapping(value="/savePermission",method = RequestMethod.POST)
	public int savePermission(@RequestBody MerchantPermission permission);
	
	@RequestMapping(value="/updatePermission",method = RequestMethod.POST)
	public int updatePermission(@RequestBody MerchantPermission permission);
	
	@RequestMapping(value="/updatePermissionSortIndex",method = RequestMethod.POST)
	public int updatePermissionSortIndex(@RequestBody MerchantPermission permission);
	
	@RequestMapping(value="/deletePermission")
	public int deletePermission(@RequestParam("id") Long id);
	
	
}
