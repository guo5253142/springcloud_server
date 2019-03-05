package com.xxyp.admin.server.service.merchant.mannger;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.merchant.MerchantModule;

public interface MerchantModuleManager {
	public List<MerchantModule> queryModuleList(MerchantModule module) ;
	public Map<Long,MerchantModule> queryModuleMapByIdSet(final Collection<Long> ids);
	public DataPage<MerchantModule> queryModuleList(DataPage<MerchantModule> page, MerchantModule module) ;
	
	public int deleteModuleById(Long id);
	
	public int saveModule(MerchantModule module);
	
	public int updateModule(MerchantModule module);
	
	public int updateModuleSortIndex(MerchantModule module);
}
