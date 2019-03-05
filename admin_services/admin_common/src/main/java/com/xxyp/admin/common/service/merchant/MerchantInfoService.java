package com.xxyp.admin.common.service.merchant;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.merchant.MerchantInfo;

public interface MerchantInfoService{
	
	@RequestMapping(value="/queryMerchantInfo",method = RequestMethod.POST)
	public List<MerchantInfo> queryMerchantInfoList(@RequestBody MerchantInfo merchantInfo);
	
	@RequestMapping(value="/getMerchantInfoById")
	public MerchantInfo getMerchantInfoById(@RequestParam("id") Long id);
	
	@RequestMapping(value="/queryMerchantInfoList",method = RequestMethod.POST)
	public PageResult<MerchantInfo> queryMerchantInfoList(@RequestBody DataPage<MerchantInfo>  page);
	
	@RequestMapping(value="/saveMerchantInfo",method = RequestMethod.POST)
	public long saveMerchantInfo(@RequestBody MerchantInfo merchantInfo);
	
	@RequestMapping(value="/updateMerchantInfo",method = RequestMethod.POST)
	public int updateMerchantInfo(@RequestBody MerchantInfo merchantInfo);
	
	@RequestMapping(value="/deleteMerchantInfo")
	public int deleteMerchantInfo(@RequestParam("id") Long id);
	
}
