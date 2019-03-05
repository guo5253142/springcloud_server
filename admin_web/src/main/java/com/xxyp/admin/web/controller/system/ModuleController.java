package com.xxyp.admin.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xxyp.admin.client.service.client.admin.system.ModuleServiceClient;
import com.xxyp.admin.client.service.client.admin.system.PermissionServiceClient;
import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.system.Module;
import com.xxyp.admin.common.model.system.Permission;
import com.xxyp.admin.web.controller.BaseController;


@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController<Module> {
	
	
	@Autowired
	private ModuleServiceClient moduleService;
	@Autowired
	private PermissionServiceClient permissionService;
	
	
	@RequestMapping("/init")
	public ModelAndView init(ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/system/module/module");
		return mv;
	}
	
	@RequestMapping("/listModule")
	public void listModule(HttpServletResponse response,DataPage<Module> page, ModelMap modelMap) {
		PageResult<Module> result = moduleService.queryModuleList(page);
		returnPageJson(response,result.getPage());
	}
	
	@RequestMapping("/addModule")
	public ModelAndView addModule(ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/system/module/addModule");
		return mv;
	}
	
	@RequestMapping("/saveModule")
	public void saveModule(HttpServletResponse response,ModelMap modelMap, Module module) {
		try {
			Module searchModule = new Module();
			searchModule.setName(module.getName());
			List<Module> moduleList = moduleService.queryModuleList(module);
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
		ModelAndView mv = new ModelAndView("page/system/module/editModule");
		Module module = moduleService.queryModuleById(id);
		
		modelMap.put("editObj", module);
		return mv;
	}
	
	@RequestMapping("/updateModule")
	public void updateModule(HttpServletResponse response,ModelMap modelMap, Module module) {
		try {
			Module searchModule = new Module();
			searchModule.setName(module.getName());
			List<Module> moduleList = moduleService.queryModuleList(searchModule);
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
			Permission permission = new Permission();
			permission.setModuleId(id);
			List<Permission> permissionList = permissionService.queryPermission(permission);
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
