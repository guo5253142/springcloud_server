package com.xxyp.admin.server.service.merchant.mannger.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.merchant.MerchantPermission;
import com.xxyp.admin.common.model.merchant.MerchantPermissionItem;
import com.xxyp.admin.common.model.merchant.MerchantUser;
import com.xxyp.admin.common.util.MapUtil;
import com.xxyp.admin.server.dao.GenericMybatisDao;
import com.xxyp.admin.server.service.merchant.mannger.MerchantUserManager;
@Component
public class MerchantUserManagerImpl implements MerchantUserManager{
	@Autowired
	private GenericMybatisDao adminDbDao;

	public List<MerchantUser> queryMerchantUserList(MerchantUser merchantUser) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(merchantUser);
		return adminDbDao.queryList("MerchantUserMapper.getMerchantUser", paramMap);
	}
	
	public DataPage<MerchantUser> queryMerchantUserList(DataPage<MerchantUser> page) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(page.getDataObj());
		return adminDbDao.queryPage("MerchantUserMapper.countMerchantUserForPage", "MerchantUserMapper.queryMerchantUserForPage", paramMap, page);
	}
	
	public long saveMerchantUser(MerchantUser merchantUser) {
		adminDbDao.insertAndSetupId("MerchantUserMapper.saveMerchantUser", merchantUser);
		return merchantUser.getId();
	}
	
	public int updateMerchantUser(MerchantUser merchantUser) {
		return adminDbDao.updateByObj("MerchantUserMapper.updateMerchantUser", merchantUser);
	}
	
	public int deleteMerchantUser(Long id) {
		if (id == null) {
			return 0;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return adminDbDao.update("MerchantUserMapper.deleteMerchantUser", paramMap);
	}

	@Override
	public MerchantUser getUserByAccount(String account) {
		Map<String,Object> map=new HashMap<>();
		map.put("account", account);
		return adminDbDao.queryUnique("MerchantUserMapper.getUserByAccount", map);
	}

	@Override
	public List<MerchantPermission> getPermissionList(MerchantUser user) {
		Map<String,Object> map=new HashMap<>();
		map.put("userId", user.getId());
		return adminDbDao.queryList("MerchantUserMapper.getPermissionList", map);
	}

	@Override
	public List<MerchantPermissionItem> getPermissionItemList(MerchantUser user) {
		Map<String,Object> map=new HashMap<>();
		map.put("userId", user.getId());
		return adminDbDao.queryList("MerchantUserMapper.getPermissionItemList", map);
	}

	@Override
	public void updateUser(MerchantUser user) {
		adminDbDao.updateByObj("MerchantUserMapper.updateUser", user);
	}
	
	
}
