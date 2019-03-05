package com.xxyp.admin.web.controller.merchant;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xxyp.admin.client.service.client.admin.merchant.MerchantModuleServiceClient;
import com.xxyp.admin.client.service.client.admin.merchant.MerchantPermissionItemServiceClient;
import com.xxyp.admin.client.service.client.admin.merchant.MerchantPermissionServiceClient;
import com.xxyp.admin.client.service.client.admin.merchant.MerchantRolePermissionServiceClient;
import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.common.constant.YesOrNoType;
import com.xxyp.admin.common.model.merchant.MerchantModule;
import com.xxyp.admin.common.model.merchant.MerchantPermission;
import com.xxyp.admin.common.model.merchant.MerchantPermissionItem;
import com.xxyp.admin.common.model.merchant.MerchantRolePermission;
import com.xxyp.admin.web.controller.BaseController;


@Controller
@RequestMapping("/merchant/permission")
public class MerchantPermissionController extends BaseController<MerchantPermission> {
	
	@Autowired
	private MerchantPermissionServiceClient permissionService;
	@Autowired
	private MerchantModuleServiceClient moduleService;
	@Autowired
	private MerchantRolePermissionServiceClient rolePermissionService;
	@Autowired
	private MerchantPermissionItemServiceClient permissionItemService;
	
	@RequestMapping("/init")
	public ModelAndView init(ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/merchant/merchantPermission/permission");
		List<MerchantModule> moduleList = moduleService.queryModuleList(new MerchantModule());
		modelMap.put("moduleList", moduleList);
		return mv;
	}
	
	@RequestMapping("/listPermission")
	public void listPermission(HttpServletResponse response,DataPage<MerchantPermission> page, MerchantPermission permission) {
		if (page == null) {
			page = new DataPage<MerchantPermission>();
		}
		page.setDataObj(permission);
		PageResult<MerchantPermission> result = permissionService.queryPermissionList(page);
		returnPageJson(response,result.getPage());
	}
	
	@RequestMapping("/addPermission")
	public ModelAndView addPermission(ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/merchant/merchantPermission/addPermission");
		List<MerchantModule> moduleList = moduleService.queryModuleList(new MerchantModule());
		List<YesOrNoType> ynList= YesOrNoType.getAll(YesOrNoType.class);
		modelMap.put("ynList", ynList);
		modelMap.put("moduleList", moduleList);
		
		return mv;
	}
	
	@RequestMapping("/savePermission")
	public void savePermission(HttpServletResponse response,MerchantPermission permission, ModelMap modelMap) {
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
		ModelAndView mv = new ModelAndView("page/merchant/merchantPermission/editPermission");
		List<MerchantModule> moduleList = moduleService.queryModuleList(new MerchantModule());
		MerchantPermission permission = permissionService.queryPermissionById(id);
		List<YesOrNoType> ynList= YesOrNoType.getAll(YesOrNoType.class);
		modelMap.put("ynList", ynList);
		modelMap.put("moduleList", moduleList);
		modelMap.put("permission", permission);
		return mv;	
	}
	
	@RequestMapping("/updatePermission")
	public void updatePermission(HttpServletResponse response,MerchantPermission permission, ModelMap modelMap) {
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
			MerchantRolePermission rolePermission = new MerchantRolePermission();
			rolePermission.setPermissionId(id);
			List<MerchantRolePermission> rolePermissionList = rolePermissionService.queryRolePermissionList(rolePermission);
			if (rolePermissionList != null && rolePermissionList.size() > 0) {
				outJson(response,this.AJAX_FAIL,"已存在引用依赖");
				return;
			}
			MerchantPermissionItem permissionItem = new MerchantPermissionItem();
			permissionItem.setPermissionId(id);
			List<MerchantPermissionItem> permissioItemList = permissionItemService.queryPermissionItemList(permissionItem);
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
