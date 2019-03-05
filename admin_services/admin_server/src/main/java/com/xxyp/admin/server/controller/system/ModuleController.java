package com.xxyp.admin.server.controller.system;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.system.Module;
import com.xxyp.admin.common.service.system.ModuleService;


@RestController
@RequestMapping("/module")
public class ModuleController{
	
	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping(value="/queryModule",method = RequestMethod.POST)
	public List<Module> queryModuleList(@RequestBody Module module){
		return moduleService.queryModuleList(module);
	}
	
	@RequestMapping(value="/queryModuleList",method = RequestMethod.POST)
	public PageResult<Module> queryModuleList(@RequestBody DataPage<Module>  page){
		return moduleService.queryModuleList(page);
	}
	
	@RequestMapping(value="/queryModuleListByIdList")
	public List<Module> queryModuleListByIdList(@RequestParam List<Long> ids){
		return moduleService.queryModuleListByIdList(ids);
	}
	
	@RequestMapping(value="/queryModuleById")
	public Module queryModuleById(@RequestParam Long id) {
		return moduleService.queryModuleById(id);
	}
	
	@RequestMapping(value="/saveModule",method = RequestMethod.POST)
	public int saveModule(@RequestBody Module module) {
		return moduleService.saveModule(module);
	}
	
	@RequestMapping(value="/deleteModuleById",method = RequestMethod.POST)
	public int deleteModuleById(@RequestParam Long id) {
		return moduleService.deleteModuleById(id);
	}
	
	@RequestMapping(value="/updateModule",method = RequestMethod.POST)
	public int updateModule(@RequestBody Module module) {
		return moduleService.updateModule(module);
	}
	
	@RequestMapping(value="/updateModuleSortIndex",method = RequestMethod.POST)
	public int updateModuleSortIndex(@RequestBody Module module) {
		return moduleService.updateModuleSortIndex(module);
	}
	
	@RequestMapping(value="/queryModuleMapByIdSet")
	public Map<Long,Module> queryModuleMapByIdSet(@RequestParam Collection<Long> ids){
		return moduleService.queryModuleMapByIdSet(ids);
	}
	
}
