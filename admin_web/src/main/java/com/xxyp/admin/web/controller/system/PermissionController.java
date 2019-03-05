package com.xxyp.admin.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xxyp.admin.client.service.client.admin.system.ModuleServiceClient;
import com.xxyp.admin.client.service.client.admin.system.PermissionItemServiceClient;
import com.xxyp.admin.client.service.client.admin.system.PermissionServiceClient;
import com.xxyp.admin.client.service.client.admin.system.RolePermissionServiceClient;
import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.common.constant.YesOrNoType;
import com.xxyp.admin.common.model.system.Module;
import com.xxyp.admin.common.model.system.Permission;
import com.xxyp.admin.common.model.system.PermissionItem;
import com.xxyp.admin.common.model.system.RolePermission;
import com.xxyp.admin.web.controller.BaseController;


@Controller
@RequestMapping("/system/permission")
public class PermissionController extends BaseController<Permission> {
	
	@Autowired
	private PermissionServiceClient permissionService;
	@Autowired
	private ModuleServiceClient moduleService;
	@Autowired
	private RolePermissionServiceClient rolePermissionService;
	@Autowired
	private PermissionItemServiceClient permissionItemService;
	
	@RequestMapping("/init")
	public ModelAndView init(ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/system/permission/permission");
		List<Module> moduleList = moduleService.queryModuleList(new Module());
		modelMap.put("moduleList", moduleList);
		return mv;
	}
	
	@RequestMapping("/listPermission")
	public void listPermission(HttpServletResponse response,DataPage<Permission> page, Permission permission) {
		if (page == null) {
			page = new DataPage<Permission>();
		}
		page.setDataObj(permission);
		PageResult<Permission> result = permissionService.queryPermissionList(page);
		returnPageJson(response,result.getPage());
	}
	
	@RequestMapping("/addPermission")
	public ModelAndView addPermission(ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/system/permission/addPermission");
		List<Module> moduleList = moduleService.queryModuleList(new Module());
		List<YesOrNoType> ynList= YesOrNoType.getAll(YesOrNoType.class);
		modelMap.put("ynList", ynList);
		modelMap.put("moduleList", moduleList);
		
		return mv;	
	}
	
	@RequestMapping("/savePermission")
	public void savePermission(HttpServletResponse response,Permission permission, ModelMap modelMap) {
		try {
			if (permissionService.savePermission(permission) != 0) {
				outJson(response,this.AJAX_SUCCESS,"新增成功");
				return;
			}
		} catch (Exception e) {
			logger.error("savePermission 异常",e);
			outJsonForException(response);
		}
	}
	
	@RequestMapping("/editPermission")
	public ModelAndView editPermission(ModelMap modelMap, Long id) {
		ModelAndView mv = new ModelAndView("page/system/permission/editPermission");
		List<Module> moduleList = moduleService.queryModuleList(new Module());
		Permission permission = permissionService.queryPermissionById(id);
		List<YesOrNoType> ynList= YesOrNoType.getAll(YesOrNoType.class);
		modelMap.put("ynList", ynList);
		modelMap.put("moduleList", moduleList);
		modelMap.put("permission", permission);
		return mv;	
	}
	
	@RequestMapping("/updatePermission")
	public void updatePermission(HttpServletResponse response,Permission permission, ModelMap modelMap) {
		try {
			if (permissionService.updatePermission(permission) != 0) {
				outJson(response,this.AJAX_SUCCESS,"修改成功");
				return;
			}
		} catch (Exception e) {
			logger.error("updatePermission 异常",e);
			outJsonForException(response);
		}
	}
	
	@RequestMapping("/deletePermission")
	public void deletePermission(HttpServletResponse response,Long id) {
		try {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setPermissionId(id);
			List<RolePermission> rolePermissionList = rolePermissionService.queryRolePermissionList(rolePermission);
			if (rolePermissionList != null && rolePermissionList.size() > 0) {
				outJson(response,this.AJAX_FAIL,"已存在引用依赖");
				return;
			}
			PermissionItem permissionItem = new PermissionItem();
			permissionItem.setPermissionId(id);
			List<PermissionItem> permissioItemList = permissionItemService.queryPermissionItemList(permissionItem);
			if (permissioItemList != null && permissioItemList.size() > 0) {
				outJson(response,this.AJAX_FAIL,"已存在引用依赖");
				return;
			}
			if (permissionService.deletePermission(id)!= 0) {
				outJson(response,this.AJAX_SUCCESS,"删除成功");
				return;
			}
		} catch (Exception e) {
			logger.error("deletePermission 异常",e);
			outJsonForException(response);
		}
		
	}
	
	
}
