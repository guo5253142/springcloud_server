package com.xxyp.admin.web.controller.merchant;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xxyp.admin.client.service.client.admin.merchant.MerchantModuleServiceClient;
import com.xxyp.admin.client.service.client.admin.merchant.MerchantPermissionServiceClient;
import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.merchant.MerchantModule;
import com.xxyp.admin.common.model.merchant.MerchantPermission;
import com.xxyp.admin.web.controller.BaseController;


@Controller
@RequestMapping("/merchant/module")
public class MerchantModuleController extends BaseController<MerchantModule> {
	
	
	@Autowired
	private MerchantModuleServiceClient moduleService;
	@Autowired
	private MerchantPermissionServiceClient permissionService;
	
	
	@RequestMapping("/init")
	public ModelAndView init(ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/merchant/merchantModule/module");
		return mv;
	}
	
	@RequestMapping("/listModule")
	public void listModule(HttpServletResponse response,DataPage<MerchantModule> page, ModelMap modelMap) {
		PageResult<MerchantModule> result = moduleService.queryModuleList(page);
		returnPageJson(response,result.getPage());
	}
	
	@RequestMapping("/addModule")
	public ModelAndView addModule(ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/merchant/merchantModule/addModule");
		return mv;
	}
	
	@RequestMapping("/saveModule")
	public void saveModule(HttpServletResponse response,ModelMap modelMap, MerchantModule module) {
		try {
			MerchantModule searchModule = new MerchantModule();
			searchModule.setName(module.getName());
			List<MerchantModule> moduleList = moduleService.queryModuleList(module);
			if (moduleList != null && moduleList.size() > 0) {
				outJson(response,this.AJAX_FAIL,"模块已存在");
				return;
			}
			if (moduleService.saveModule(module) != 0) {
				outJson(response,this.AJAX_SUCCESS,"新增模块成功");
				return;
			}
		} catch (Exception e) {
			logger.error("saveModule 异常",e);
			outJsonForException(response);
		}
	}
	
	@RequestMapping("/editModule")
	public ModelAndView editModule(ModelMap modelMap, Long id) {
		ModelAndView mv = new ModelAndView("page/merchant/merchantModule/addModule");
		MerchantModule module = moduleService.queryModuleById(id);
		
		modelMap.put("editObj", module);
		return mv;
	}
	
	@RequestMapping("/updateModule")
	public void updateModule(HttpServletResponse response,ModelMap modelMap, MerchantModule module) {
		try {
			MerchantModule searchModule = new MerchantModule();
			searchModule.setName(module.getName());
			List<MerchantModule> moduleList = moduleService.queryModuleList(searchModule);
			if (moduleList != null && moduleList.size() > 0 && 
					moduleList.get(0).getId().longValue() != module.getId().longValue()) {
				outJson(response,this.AJAX_FAIL,"模块已存在");
				return;
			}
			if (moduleService.updateModule(module) != 0) {
				outJson(response,this.AJAX_SUCCESS,"修改成功");
				return;
			}
		} catch (Exception e) {
			logger.error("updateModule 异常",e);
			outJsonForException(response);
		}
	}
	
	@RequestMapping("/deleteModule")
	public void deleteModule(HttpServletResponse response,Long id) {
		try {
			MerchantPermission permission = new MerchantPermission();
			permission.setModuleId(id);
			List<MerchantPermission> permissionList = permissionService.queryPermission(permission);
			if(null!=permissionList && permissionList.size()>0){
				outJson(response,this.AJAX_FAIL,"该模块下配有菜单权限，请先删除菜单权限");
			}else{
				if (moduleService.deleteModuleById(id) != 0) {
					outJson(response,this.AJAX_SUCCESS,"删除成功");
					return;
				}
			}
		} catch (Exception e) {
			logger.error("deleteModule 异常",e);
			outJsonForException(response);
		}
		
	}
}
