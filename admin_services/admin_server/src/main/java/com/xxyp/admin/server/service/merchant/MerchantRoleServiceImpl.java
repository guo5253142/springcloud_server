package com.xxyp.admin.server.service.merchant;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.merchant.MerchantRole;
import com.xxyp.admin.common.service.merchant.MerchantRoleService;
import com.xxyp.admin.server.annotation.WriteDataSource;
import com.xxyp.admin.server.service.merchant.mannger.MerchantRoleManager;

@Service
public class MerchantRoleServiceImpl implements MerchantRoleService {
	@Autowired
	public MerchantRoleManager roleManager;

	@WriteDataSource
	public List<MerchantRole> queryRoleList(MerchantRole role) {
		return roleManager.queryRoleList(role);
	}

	@WriteDataSource
	public PageResult<MerchantRole> queryRoleList(DataPage<MerchantRole> page) {
		page = roleManager.queryRoleList(page);
		PageResult<MerchantRole> result = new PageResult<MerchantRole>();
		result.setPage(page);
		return result;
	}

	@WriteDataSource
	public long saveRole(MerchantRole role) {
		return roleManager.saveRole(role);
	}
	
	@WriteDataSource
	public int updateRole(MerchantRole role) {
		return roleManager.updateRole(role);
	}
	
	@WriteDataSource
	public int deleteRole(Long id) {
		return roleManager.deleteRole(id);
	}

	@WriteDataSource
	public MerchantRole getRoleById(Long id) {
		if (id == null) {
			return null;
		}
		MerchantRole role = new MerchantRole();
		role.setId(id);
		List<MerchantRole> list = queryRoleList(role);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	
}
