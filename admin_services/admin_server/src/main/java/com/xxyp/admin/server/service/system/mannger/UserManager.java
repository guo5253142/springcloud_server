package com.xxyp.admin.server.service.system.mannger;

import java.util.List;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.system.Permission;
import com.xxyp.admin.common.model.system.PermissionItem;
import com.xxyp.admin.common.model.system.User;



public interface UserManager {
	
	public List<User> queryAllUserList();
	
	public User getUserByAccount(String account);
	
	public List<Permission> getPermissionList(User user);
	
	public List<PermissionItem> getPermissionItemList(User user);
	
	public void updateUser(User user);
	
	public DataPage<User> queryUser(DataPage<User>  page);
	
	public void saveUser(User user);
	
	public void deleteUser(User user);

	public User getUser(User user);
	
	public void saveEditUser(User user);
}
