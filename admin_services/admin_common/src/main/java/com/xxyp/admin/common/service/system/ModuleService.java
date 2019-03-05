package com.xxyp.admin.common.service.system;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.system.Module;

public interface ModuleService {
	
	@RequestMapping(value="/queryModule",method = RequestMethod.POST)
	public List<Module> queryModuleList(@RequestBody Module module);
	
	@RequestMapping(value="/queryModuleList",method = RequestMethod.POST)
	public PageResult<Module> queryModuleList(@RequestBody DataPage<Module>  page);
	
	@RequestMapping(value="/queryModuleListByIdList")
	public List<Module> queryModuleListByIdList(@RequestParam("ids") List<Long> ids);
	
	@RequestMapping(value="/queryModuleById")
	public Module queryModuleById(@RequestParam("id") Long id);
	
	@RequestMapping(value="/saveModule",method = RequestMethod.POST)
	public int saveModule(@RequestBody Module module);
	
	@RequestMapping(value="/deleteModuleById",method = RequestMethod.POST)
	public int deleteModuleById(@RequestParam("id") Long id);
	
	@RequestMapping(value="/updateModule",method = RequestMethod.POST)
	public int updateModule(@RequestBody Module module);
	
	@RequestMapping(value="/updateModuleSortIndex",method = RequestMethod.POST)
	public int updateModuleSortIndex(@RequestBody Module module);
	
	@RequestMapping(value="/queryModuleMapByIdSet")
	public Map<Long,Module> queryModuleMapByIdSet(@RequestParam("ids") Collection<Long> ids);
}
