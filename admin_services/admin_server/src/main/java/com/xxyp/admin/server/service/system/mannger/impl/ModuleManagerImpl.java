package com.xxyp.admin.server.service.system.mannger.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.system.Module;
import com.xxyp.admin.common.util.MapUtil;
import com.xxyp.admin.server.dao.GenericMybatisDao;
import com.xxyp.admin.server.service.system.mannger.ModuleManager;
@Component
public class ModuleManagerImpl implements ModuleManager{
	@Autowired
	private GenericMybatisDao adminDbDao;

	public List<Module> queryModuleList(Module module) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(module);
		return adminDbDao.queryList("ModuleMapper.getModule", paramMap);
	}
	public Map<Long,Module> queryModuleMapByIdSet(final Collection<Long> ids){
		final  Map<Long,Module> resultMap = new  HashMap<Long,Module>();
		
		if (ids == null || ids.isEmpty()) {
			return resultMap;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idList", ids);
		List<Module> moduleList = adminDbDao.queryList("ModuleMapper.queryModuleByIdSet", paramMap);
		for(Module per : moduleList){
			resultMap.put(per.getId(), per);
		}
		
		return resultMap;
	}
	
	public DataPage<Module> queryModuleList(DataPage<Module> page, Module module) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(module);
		return adminDbDao.queryPage("ModuleMapper.countModuleForPage", "ModuleMapper.queryModuleForPage", paramMap, page);
	}
	
	public int deleteModuleById(Long id) {
		if (id == null) {
			return 0;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return adminDbDao.update("ModuleMapper.deleteModule", paramMap);
	}
	
	public int saveModule(Module module) {
		if (module == null) {
			return 0;
		}
		/*Long  sortIndex = adminDbDao.generateSequence("ModuleMapper.getMaxSortIndex");
		if (sortIndex != null) {
			module.setOrderIndex(sortIndex.intValue() + 1);
		}*/
		return adminDbDao.insertAndSetupId("ModuleMapper.saveModule", module);
	}
	
	public int updateModule(Module module) {
		if (module == null) {
			return 0;
		}
		return adminDbDao.updateByObj("ModuleMapper.updateModule", module);
	}
	
	public int updateModuleSortIndex(Module module) {
		if (module.getOrderIndex().intValue() == 0) {
			/*Long  sortIndex = adminDbDao.generateSequence("ModuleMapper.getMaxSortIndex");
			if (sortIndex != null) {
				module.setOrderIndex(sortIndex.intValue() + 1);
			}*/
		}
		return adminDbDao.updateByObj("ModuleMapper.updateModuleSortIndex", module);
	}
	
	
}
