package com.xxyp.admin.server.service.system.mannger.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.system.Permission;
import com.xxyp.admin.common.model.system.PermissionItem;
import com.xxyp.admin.common.model.system.User;
import com.xxyp.admin.common.util.MapUtil;
import com.xxyp.admin.server.dao.GenericMybatisDao;
import com.xxyp.admin.server.service.system.mannger.UserManager;
@Component
public class UserManagerImpl implements UserManager {
	@Autowired
	private GenericMybatisDao adminDbDao;


	@Override
	public List<User> queryAllUserList() {
		return adminDbDao.queryList("UserMapper.queryAllUserList", null);
	}

	@Override
	public User getUserByAccount(String account) {
		Map<String,Object> map=new HashMap<>();
		map.put("account", account);
		return adminDbDao.queryUnique("UserMapper.getUserByAccount", map);
	}

	@Override
	public void updateUser(User user) {
		adminDbDao.updateByObj("UserMapper.updateUser", user);
	}

	@Override
	public List<Permission> getPermissionList(User user) {
		Map<String,Object> map=new HashMap<>();
		map.put("userId", user.getId());
		return adminDbDao.queryList("UserMapper.getPermissionList", map);
	}

	@Override
	public List<PermissionItem> getPermissionItemList(User user) {
		Map<String,Object> map=new HashMap<>();
		map.put("userId", user.getId());
		return adminDbDao.queryList("UserMapper.getPermissionItemList", map);
	}

	@Override
	public DataPage<User> queryUser(DataPage<User> page) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(page.getDataObj());
		return adminDbDao.queryPage("UserMapper.countUserForPage", "UserMapper.queryUserForPage", paramMap, page);
	}

	@Override
	public void saveUser(User user) {
		adminDbDao.insertAndSetupId("UserMapper.insert", user);
		if(null!=user.getRoleId()){
			adminDbDao.insertAndSetupId("UserMapper.insertUserRole", user);
		}
		
	}

	@Override
	public void deleteUser(User user) {
		adminDbDao.updateByObj("UserMapper.delete", user);
		adminDbDao.updateByObj("UserMapper.deleteUserRole", user);
	}

	@Override
	public User getUser(User user) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(user);
		return adminDbDao.queryUnique("UserMapper.get", paramMap);
	}

	@Override
	public void saveEditUser(User user) {
		adminDbDao.updateByObj("UserMapper.updateUser", user);
		adminDbDao.updateByObj("UserMapper.deleteUserRole", user);
		if(null!=user.getRoleId()){
			adminDbDao.insertAndSetupId("UserMapper.insertUserRole", user);
		}
	}
	
}
