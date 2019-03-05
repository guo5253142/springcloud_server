package com.xxyp.admin.server.controller.system;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.system.Permission;
import com.xxyp.admin.common.model.system.User;
import com.xxyp.admin.common.model.system.vo.AdminMenuVo;
import com.xxyp.admin.common.model.system.vo.UserSessionVo;
import com.xxyp.admin.common.service.system.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;

	@RequestMapping("/getUserByAccount")
	public User getUserByAccount(HttpServletRequest request, String account) {
		User user = this.userService.getUserByAccount(account);
		return user;
	}
	
	@RequestMapping("/getPermission")
	public UserSessionVo getPermission(HttpServletRequest request, @RequestBody User user) {
		return this.userService.getPermission(user);
	}
	
	@RequestMapping("/updateUser")
	public void updateUser(HttpServletRequest request, @RequestBody User user) {
		 this.userService.updateUser(user);
	}
	
	@RequestMapping("/getMenu")
	public Map<String, List<AdminMenuVo>>  getMenu(HttpServletRequest request, @RequestBody Map<String, Permission> permissionMap) {
		 return this.userService.getMenu(permissionMap);
	}
	
	@RequestMapping("/queryUser")
	public PageResult<User> queryUser(@RequestBody DataPage<User> page){
		return userService.queryUser(page);
	}
	
	@RequestMapping("/saveEditUser")
	public void saveEditUser(@RequestBody User user){
		userService.saveEditUser(user);
	}
	
	
	@RequestMapping("/saveUser")
	public void saveUser(@RequestBody User user){
		userService.saveUser(user);
	}
	
	
	@RequestMapping("/deleteUser")
	public void deleteUser(@RequestBody User user){
		userService.deleteUser(user);
	}
	
	@RequestMapping("/getUser")
	public User getUser(@RequestBody User user){
		return userService.getUser(user);
	}
	
	
}