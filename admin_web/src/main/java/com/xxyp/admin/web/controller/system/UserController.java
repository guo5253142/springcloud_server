package com.xxyp.admin.web.controller.system;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xxyp.admin.client.service.client.admin.system.RoleServiceClient;
import com.xxyp.admin.client.service.client.admin.system.UserServiceClient;
import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.common.constant.UsedTag;
import com.xxyp.admin.common.model.system.Role;
import com.xxyp.admin.common.model.system.User;
import com.xxyp.admin.common.model.system.constant.UserStatus;
import com.xxyp.admin.common.util.MD5;
import com.xxyp.admin.web.common.constant.Constants;
import com.xxyp.admin.web.controller.BaseController;

@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController<User>{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private UserServiceClient userServiceClient;
	@Resource
	private RoleServiceClient RoleServiceClient;

	@RequestMapping("/init")
	public ModelAndView init(ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/system/user/user");
		List<Role> roleList = RoleServiceClient.queryRoleList(new Role());
		modelMap.put("roleList", roleList);
		return mv;
	}
	@RequestMapping("/listUser")
	public void listUser(User vo,HttpServletResponse response, DataPage<User> page) {
		page.setDataObj(vo);
		PageResult<User> result = userServiceClient.queryUser(page);
		returnPageJson(response,result.getPage());
	}

	@RequestMapping("/addUser")
	public ModelAndView addUser(ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/system/user/addUser");
		List<Role> roleList = RoleServiceClient.queryRoleList(new Role());
		List<UserStatus> statusList=UserStatus.getAll(UserStatus.class);
		modelMap.put("roleList", roleList);
		modelMap.put("statusList", statusList);
		return mv;
	}

	@RequestMapping("/saveUser")
	public void saveUser(HttpServletRequest request,HttpServletResponse response,User user) {
		try {
			if (userServiceClient.getUserByAccount(user.getAccount()) != null) {
				outJson(response,this.AJAX_FAIL,"用户名已存在");
			}else{
				user.setPassword(MD5.encode1(Constants.DEFAULT_PASSWD));
				user.setUsedTag(UsedTag.enabled.getIndex());
				user.setCreator(getUser(request).getUser().getId());
				user.setCreateDate(new Date());
				userServiceClient.saveUser(user);
				outJson(response,this.AJAX_SUCCESS,"添加用户成功");
			}
		} catch (Exception e) {
			logger.error("saveUser 异常",e);
			outJsonForException(response);
		}
	}

	@RequestMapping("/editUser")
	public ModelAndView editUser(Long id, ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/system/user/editUser");
		User user=new User();
		user.setId(id);
		user = userServiceClient.getUser(user);
		List<Role> roleList = RoleServiceClient.queryRoleList(new Role());
		List<UserStatus> statusList=UserStatus.getAll(UserStatus.class);
		modelMap.put("roleList", roleList);
		modelMap.put("statusList", statusList);
		modelMap.put("user", user);
		return mv;
	}

	@RequestMapping("/updateUser")
	public void updateUser(HttpServletResponse response,User user) {
		try {
			userServiceClient.saveEditUser(user);
			outJson(response,this.AJAX_SUCCESS,"更新用户信息成功");
		} catch (Exception e) {
			logger.error("updateUser 异常",e);
			outJsonForException(response);
		}
	}

	@RequestMapping("/deleteUser")
	public void deleteUser(HttpServletResponse response,Long id) {
		try {
			if(id.longValue()!=this.ADMIN_ID){
				User user = new User();
				user.setId(id);
				userServiceClient.deleteUser(user);
				outJson(response,this.AJAX_SUCCESS,"删除用户成功");
			}else{
				outJson(response,this.AJAX_FAIL,"无法删除系统管理员");
			}
		} catch (Exception e) {
			logger.error("deleteUser 异常",e);
			outJsonForException(response);
		}
		
	}
	
}
