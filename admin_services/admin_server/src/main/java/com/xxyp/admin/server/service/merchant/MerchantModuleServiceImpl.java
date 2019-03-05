package com.xxyp.admin.server.service.merchant;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.merchant.MerchantModule;
import com.xxyp.admin.common.service.merchant.MerchantModuleService;
import com.xxyp.admin.server.annotation.WriteDataSource;
import com.xxyp.admin.server.service.merchant.mannger.MerchantModuleManager;
@Service
public class MerchantModuleServiceImpl implements MerchantModuleService {
	@Autowired
	public MerchantModuleManager ModuleManager;

	@WriteDataSource
	public List<MerchantModule> queryModuleList(MerchantModule module) {
		return ModuleManager.queryModuleList(module);
	}

	@WriteDataSource
	public PageResult<MerchantModule> queryModuleList(DataPage<MerchantModule> page) {
		page = ModuleManager.queryModuleList(page, page.getDataObj());
		PageResult<MerchantModule> result = new PageResult<MerchantModule>();
		result.setPage(page);
		return result;
	}

	@WriteDataSource
	public List<MerchantModule> queryModuleListByIdList(List<Long> ids) {
		if (ids == null) {
			return null;
		}
		List<MerchantModule> result = new ArrayList<MerchantModule>();
		List<MerchantModule> modules = null;
		MerchantModule module = new MerchantModule();
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
	public MerchantModule queryModuleById(Long id) {
		if (id == null) {
			return null;
		}
		List<MerchantModule> modules = null;
		MerchantModule module = new MerchantModule();
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
	public int saveModule(MerchantModule module) {
		return ModuleManager.saveModule(module);
	}

	@WriteDataSource
	public int updateModule(MerchantModule module) {
		return ModuleManager.updateModule(module);
	}
	
	@WriteDataSource
	public int updateModuleSortIndex(MerchantModule module) {
		return ModuleManager.updateModuleSortIndex(module);
	}

	@WriteDataSource
	public Map<Long, MerchantModule> queryModuleMapByIdSet(Collection<Long> ids) {
		 
		return ModuleManager.queryModuleMapByIdSet(ids);
	}
	
}
