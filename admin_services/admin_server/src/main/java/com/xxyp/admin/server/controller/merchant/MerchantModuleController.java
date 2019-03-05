package com.xxyp.admin.server.controller.merchant;

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
import com.xxyp.admin.common.model.merchant.MerchantModule;
import com.xxyp.admin.common.service.merchant.MerchantModuleService;


@RestController
@RequestMapping("/merchantModule")
public class MerchantModuleController{
	
	@Autowired
	private MerchantModuleService moduleService;
	
	@RequestMapping(value="/queryModule",method = RequestMethod.POST)
	public List<MerchantModule> queryModuleList(@RequestBody MerchantModule module){
		return moduleService.queryModuleList(module);
	}
	
	@RequestMapping(value="/queryModuleList",method = RequestMethod.POST)
	public PageResult<MerchantModule> queryModuleList(@RequestBody DataPage<MerchantModule>  page){
		return moduleService.queryModuleList(page);
	}
	
	@RequestMapping(value="/queryModuleListByIdList")
	public List<MerchantModule> queryModuleListByIdList(@RequestParam List<Long> ids){
		return moduleService.queryModuleListByIdList(ids);
	}
	
	@RequestMapping(value="/queryModuleById")
	public MerchantModule queryModuleById(@RequestParam Long id) {
		return moduleService.queryModuleById(id);
	}
	
	@RequestMapping(value="/saveModule",method = RequestMethod.POST)
	public int saveModule(@RequestBody MerchantModule module) {
		return moduleService.saveModule(module);
	}
	
	@RequestMapping(value="/deleteModuleById",method = RequestMethod.POST)
	public int deleteModuleById(@RequestParam Long id) {
		return moduleService.deleteModuleById(id);
	}
	
	@RequestMapping(value="/updateModule",method = RequestMethod.POST)
	public int updateModule(@RequestBody MerchantModule module) {
		return moduleService.updateModule(module);
	}
	
	@RequestMapping(value="/updateModuleSortIndex",method = RequestMethod.POST)
	public int updateModuleSortIndex(@RequestBody MerchantModule module) {
		return moduleService.updateModuleSortIndex(module);
	}
	
	@RequestMapping(value="/queryModuleMapByIdSet")
	public Map<Long,MerchantModule> queryModuleMapByIdSet(@RequestParam Collection<Long> ids){
		return moduleService.queryModuleMapByIdSet(ids);
	}
	
}
