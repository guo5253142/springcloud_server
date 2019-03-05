package com.xxyp.admin.server.controller.merchant;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.merchant.MerchantInfo;
import com.xxyp.admin.common.service.merchant.MerchantInfoService;

/**
 * 商户信息
 */
@RestController
@RequestMapping("/merchantInfo")
public class MerchantInfoController {

	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@RequestMapping(value="/queryMerchantInfo",method = RequestMethod.POST)
	public List<MerchantInfo> queryMerchantInfoList(@RequestBody MerchantInfo merchantInfo){
		return merchantInfoService.queryMerchantInfoList(merchantInfo);
	}
	
	@RequestMapping(value="/getMerchantInfoById")
	public MerchantInfo getMerchantInfoById(@RequestParam Long id) {
		return merchantInfoService.getMerchantInfoById(id);
	}
	
	@RequestMapping(value="/queryMerchantInfoList",method = RequestMethod.POST)
	public PageResult<MerchantInfo> queryMerchantInfoList(@RequestBody DataPage<MerchantInfo>  page){
		return merchantInfoService.queryMerchantInfoList(page);
	}
	
	@RequestMapping(value="/saveMerchantInfo",method = RequestMethod.POST)
	public long saveMerchantInfo(@RequestBody MerchantInfo merchantInfo) {
		return merchantInfoService.saveMerchantInfo(merchantInfo);
	}
	
	@RequestMapping(value="/updateMerchantInfo",method = RequestMethod.POST)
	public int updateMerchantInfo(@RequestBody MerchantInfo merchantInfo) {
		return merchantInfoService.updateMerchantInfo(merchantInfo);
	}
	
	@RequestMapping(value="/deleteMerchantInfo")
	public int deleteMerchantInfo(@RequestParam Long id) {
		return merchantInfoService.deleteMerchantInfo(id);
	}
	
}
