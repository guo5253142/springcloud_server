package com.xxyp.sso.server.service.system;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.system.Permission;
import com.xxyp.admin.common.model.system.User;
import com.xxyp.admin.common.model.system.vo.AdminMenuVo;
import com.xxyp.admin.common.model.system.vo.UserSessionVo;
import com.xxyp.admin.common.service.system.UserService;
import com.xxyp.sso.server.annotation.WriteDataSource;
import com.xxyp.sso.server.service.system.mannger.UserManager;

@Service
public class UserServiceImpl implements UserService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public UserManager userManager;
	
	@WriteDataSource
	public User getUserByAccount(String account) {
		return userManager.getUserByAccount(account);
	}

	@WriteDataSource
	public void updateUser(User user) {
		userManager.updateUser(user);
	}

	@Override
	public UserSessionVo getPermission(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveEditUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<AdminMenuVo>> getMenu(Map<String, Permission> permissionMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResult<User> queryUser(DataPage<User> page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}



}