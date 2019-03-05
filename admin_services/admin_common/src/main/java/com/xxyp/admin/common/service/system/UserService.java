package com.xxyp.admin.common.service.system;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.system.Permission;
import com.xxyp.admin.common.model.system.User;
import com.xxyp.admin.common.model.system.vo.AdminMenuVo;
import com.xxyp.admin.common.model.system.vo.UserSessionVo;

public interface UserService {
	
	
	/**
	 * 查询用户信息
	 * @param account 账号
	 * @return
	 */
	@RequestMapping(value="/getUserByAccount" )
	public User getUserByAccount(@RequestParam("account") String account);
	
	/**
	 * 查询权限
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/getPermission",method = RequestMethod.POST)
	public UserSessionVo getPermission(@RequestBody User user);
	
	/**
	 * 修改user数据
	 * @param user
	 */
	@RequestMapping(value="/updateUser",method = RequestMethod.POST )
	public void updateUser(@RequestBody User user);
	
	/**
	 * 修改页面更新user数据
	 * @param user
	 */
	@RequestMapping(value="/saveEditUser",method = RequestMethod.POST )
	public void saveEditUser(@RequestBody User user);
	
	/**
	 * 根据权限获取菜单
	 * @param permissionMap
	 * @return
	 */
	@RequestMapping(value="/getMenu",method = RequestMethod.POST )
	public Map<String,List<AdminMenuVo>> getMenu(@RequestBody Map<String, Permission> permissionMap);
	
	/**
	 * 查询用户列表
	 * @param page
	 * @param condition
	 * @return
	 */
	@RequestMapping(value="/queryUser",method = RequestMethod.POST )
	public PageResult<User> queryUser(@RequestBody DataPage<User> page);
	
	/**
	 * 新增用户
	 * @param user
	 */
	@RequestMapping(value="/saveUser",method = RequestMethod.POST )
	public void saveUser(@RequestBody User user);
	
	/**
	 * 删除用户
	 * @param user
	 */
	@RequestMapping(value="/deleteUser",method = RequestMethod.POST )
	public void deleteUser(@RequestBody User user);
	
	/**
	 * 根据条件查询user
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/getUser",method = RequestMethod.POST )
	public User getUser(@RequestBody User user);
	
}
