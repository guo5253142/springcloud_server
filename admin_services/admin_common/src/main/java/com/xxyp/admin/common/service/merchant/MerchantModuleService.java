package com.xxyp.admin.common.service.merchant;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.merchant.MerchantModule;

public interface MerchantModuleService {
	
	@RequestMapping(value="/queryModule",method = RequestMethod.POST)
	public List<MerchantModule> queryModuleList(@RequestBody MerchantModule module);
	
	@RequestMapping(value="/queryModuleList",method = RequestMethod.POST)
	public PageResult<MerchantModule> queryModuleList(@RequestBody DataPage<MerchantModule>  page);
	
	@RequestMapping(value="/queryModuleListByIdList")
	public List<MerchantModule> queryModuleListByIdList(@RequestParam("ids") List<Long> ids);
	
	@RequestMapping(value="/queryModuleById")
	public MerchantModule queryModuleById(@RequestParam("id") Long id);
	
	@RequestMapping(value="/saveModule",method = RequestMethod.POST)
	public int saveModule(@RequestBody MerchantModule module);
	
	@RequestMapping(value="/deleteModuleById",method = RequestMethod.POST)
	public int deleteModuleById(@RequestParam("id") Long id);
	
	@RequestMapping(value="/updateModule",method = RequestMethod.POST)
	public int updateModule(@RequestBody MerchantModule module);
	
	@RequestMapping(value="/updateModuleSortIndex",method = RequestMethod.POST)
	public int updateModuleSortIndex(@RequestBody MerchantModule module);
	
	@RequestMapping(value="/queryModuleMapByIdSet")
	public Map<Long,MerchantModule> queryModuleMapByIdSet(@RequestParam("ids") Collection<Long> ids);
}
