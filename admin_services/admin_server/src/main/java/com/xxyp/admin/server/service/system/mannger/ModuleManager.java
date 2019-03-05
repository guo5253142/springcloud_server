package com.xxyp.admin.server.service.system.mannger;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.system.Module;

public interface ModuleManager {
	public List<Module> queryModuleList(Module module) ;
	public Map<Long,Module> queryModuleMapByIdSet(final Collection<Long> ids);
	public DataPage<Module> queryModuleList(DataPage<Module> page, Module module) ;
	
	public int deleteModuleById(Long id);
	
	public int saveModule(Module module);
	
	public int updateModule(Module module);
	
	public int updateModuleSortIndex(Module module);
}
