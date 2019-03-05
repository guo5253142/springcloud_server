package com.xxyp.admin.server.service.merchant.mannger;

import java.util.List;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.merchant.MerchantPermission;
import com.xxyp.admin.common.model.merchant.MerchantPermissionItem;
import com.xxyp.admin.common.model.merchant.MerchantUser;
import com.xxyp.admin.common.model.system.User;

public interface MerchantUserManager {
	public List<MerchantUser> queryMerchantUserList(MerchantUser merchantUser);
	
	public DataPage<MerchantUser> queryMerchantUserList(DataPage<MerchantUser> page);
	public long saveMerchantUser(MerchantUser merchantUser);
	
	public int updateMerchantUser(MerchantUser merchantUser);
	
	public int deleteMerchantUser(Long id);
	
	public MerchantUser getUserByAccount(String account);
	
	public List<MerchantPermission> getPermissionList(MerchantUser user);
	
	public List<MerchantPermissionItem> getPermissionItemList(MerchantUser user);
	
	public void updateUser(MerchantUser user);
	

}
