package com.xxyp.admin.server.service.merchant.mannger;

import java.util.List;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.merchant.MerchantRole;

public interface MerchantRoleManager {
	public List<MerchantRole> queryRoleList(MerchantRole role);
	
	public DataPage<MerchantRole> queryRoleList(DataPage<MerchantRole> page);
	public long saveRole(MerchantRole role);
	
	public int updateRole(MerchantRole role);
	
	public int deleteRole(Long id);
	

}
