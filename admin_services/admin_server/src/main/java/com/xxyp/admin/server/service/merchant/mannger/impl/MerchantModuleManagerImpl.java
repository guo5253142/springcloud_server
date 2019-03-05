package com.xxyp.admin.server.service.merchant.mannger.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.merchant.MerchantModule;
import com.xxyp.admin.common.util.MapUtil;
import com.xxyp.admin.server.dao.GenericMybatisDao;
import com.xxyp.admin.server.service.merchant.mannger.MerchantModuleManager;
@Component
public class MerchantModuleManagerImpl implements MerchantModuleManager{
	@Autowired
	private GenericMybatisDao adminDbDao;

	public List<MerchantModule> queryModuleList(MerchantModule module) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(module);
		return adminDbDao.queryList("MerchantModuleMapper.getModule", paramMap);
	}
	public Map<Long,MerchantModule> queryModuleMapByIdSet(final Collection<Long> ids){
		final  Map<Long,MerchantModule> resultMap = new  HashMap<Long,MerchantModule>();
		
		if (ids == null || ids.isEmpty()) {
			return resultMap;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idList", ids);
		List<MerchantModule> moduleList = adminDbDao.queryList("MerchantModuleMapper.queryModuleByIdSet", paramMap);
		for(MerchantModule per : moduleList){
			resultMap.put(per.getId(), per);
		}
		
		return resultMap;
	}
	
	public DataPage<MerchantModule> queryModuleList(DataPage<MerchantModule> page, MerchantModule module) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(module);
		return adminDbDao.queryPage("MerchantModuleMapper.countModuleForPage", "MerchantModuleMapper.queryModuleForPage", paramMap, page);
	}
	
	public int deleteModuleById(Long id) {
		if (id == null) {
			return 0;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return adminDbDao.update("MerchantModuleMapper.deleteModule", paramMap);
	}
	
	public int saveModule(MerchantModule module) {
		if (module == null) {
			return 0;
		}
		/*Long  sortIndex = adminDbDao.generateSequence("MerchantModuleMapper.getMaxSortIndex");
		if (sortIndex != null) {
			module.setOrderIndex(sortIndex.intValue() + 1);
		}*/
		return adminDbDao.insertAndSetupId("MerchantModuleMapper.saveModule", module);
	}
	
	public int updateModule(MerchantModule module) {
		if (module == null) {
			return 0;
		}
		return adminDbDao.updateByObj("MerchantModuleMapper.updateModule", module);
	}
	
	public int updateModuleSortIndex(MerchantModule module) {
		if (module.getOrderIndex().intValue() == 0) {
			/*Long  sortIndex = adminDbDao.generateSequence("MerchantModuleMapper.getMaxSortIndex");
			if (sortIndex != null) {
				module.setOrderIndex(sortIndex.intValue() + 1);
			}*/
		}
		return adminDbDao.updateByObj("MerchantModuleMapper.updateModuleSortIndex", module);
	}
	
}
