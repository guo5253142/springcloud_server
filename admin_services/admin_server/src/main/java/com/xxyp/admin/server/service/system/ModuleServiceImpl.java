package com.xxyp.admin.server.service.system;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.system.Module;
import com.xxyp.admin.common.service.system.ModuleService;
import com.xxyp.admin.server.annotation.WriteDataSource;
import com.xxyp.admin.server.service.system.mannger.ModuleManager;
@Service
public class ModuleServiceImpl implements ModuleService {
	@Autowired
	public ModuleManager ModuleManager;

	@WriteDataSource
	public List<Module> queryModuleList(Module module) {
		return ModuleManager.queryModuleList(module);
	}

	@WriteDataSource
	public PageResult<Module> queryModuleList(DataPage<Module> page) {
		page = ModuleManager.queryModuleList(page, page.getDataObj());
		PageResult<Module> result = new PageResult<Module>();
		result.setPage(page);
		return result;
	}

	@WriteDataSource
	public List<Module> queryModuleListByIdList(List<Long> ids) {
		if (ids == null) {
			return null;
		}
		List<Module> result = new ArrayList<Module>();
		List<Module> modules = null;
		Module module = new Module();
		for (Long id : ids) {
			module.setId(id);
			modules = queryModuleList(module);
			if (modules != null) {
				result.add(modules.get(0));
			}
		}
		return result;
	}
	
	
	
	@WriteDataSource
	public Module queryModuleById(Long id) {
		if (id == null) {
			return null;
		}
		List<Module> modules = null;
		Module module = new Module();
		module.setId(id);
		modules = queryModuleList(module);
		if (modules != null) {
			return modules.get(0);
		}
		return null;
	}
	
	@WriteDataSource
	public int deleteModuleById(Long id) {
		if (id == null) {
			return 0;
		}
		return ModuleManager.deleteModuleById(id);
	}

	@WriteDataSource
	public int saveModule(Module module) {
		return ModuleManager.saveModule(module);
	}

	@WriteDataSource
	public int updateModule(Module module) {
		return ModuleManager.updateModule(module);
	}
	
	@WriteDataSource
	public int updateModuleSortIndex(Module module) {
		return ModuleManager.updateModuleSortIndex(module);
	}

	@WriteDataSource
	public Map<Long, Module> queryModuleMapByIdSet(Collection<Long> ids) {
		 
		return ModuleManager.queryModuleMapByIdSet(ids);
	}
	
}
