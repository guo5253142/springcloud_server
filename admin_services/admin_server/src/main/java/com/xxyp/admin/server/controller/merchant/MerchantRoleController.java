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

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.merchant.MerchantRole;
import com.xxyp.admin.common.service.merchant.MerchantRoleService;

@RestController
@RequestMapping("/merchantRole")
public class MerchantRoleController {

	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MerchantRoleService roleService;
	
	@RequestMapping(value="/queryRole",method = RequestMethod.POST)
	public List<MerchantRole> queryRoleList(@RequestBody MerchantRole role){
		return roleService.queryRoleList(role);
	}
	
	@RequestMapping(value="/getRoleById")
	public MerchantRole getRoleById(@RequestParam Long id) {
		return roleService.getRoleById(id);
	}
	
	@RequestMapping(value="/queryRoleList",method = RequestMethod.POST)
	public PageResult<MerchantRole> queryRoleList(@RequestBody DataPage<MerchantRole>  page){
		return roleService.queryRoleList(page);
	}
	
	@RequestMapping(value="/saveRole",method = RequestMethod.POST)
	public long saveRole(@RequestBody MerchantRole role) {
		return roleService.saveRole(role);
	}
	
	@RequestMapping(value="/updateRole",method = RequestMethod.POST)
	public int updateRole(@RequestBody MerchantRole role) {
		return roleService.updateRole(role);
	}
	
	@RequestMapping(value="/deleteRole")
	public int deleteRole(@RequestParam Long id) {
		return roleService.deleteRole(id);
	}
	
}
