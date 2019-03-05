package com.xxyp.admin.common.service.merchant;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.merchant.MerchantRole;

public interface MerchantRoleService{
	
	@RequestMapping(value="/queryRole",method = RequestMethod.POST)
	public List<MerchantRole> queryRoleList(@RequestBody MerchantRole role);
	
	@RequestMapping(value="/getRoleById")
	public MerchantRole getRoleById(@RequestParam("id") Long id);
	
	@RequestMapping(value="/queryRoleList",method = RequestMethod.POST)
	public PageResult<MerchantRole> queryRoleList(@RequestBody DataPage<MerchantRole>  page);
	
	@RequestMapping(value="/saveRole",method = RequestMethod.POST)
	public long saveRole(@RequestBody MerchantRole role);
	
	@RequestMapping(value="/updateRole",method = RequestMethod.POST)
	public int updateRole(@RequestBody MerchantRole role);
	
	@RequestMapping(value="/deleteRole")
	public int deleteRole(@RequestParam("id") Long id);
	
}
