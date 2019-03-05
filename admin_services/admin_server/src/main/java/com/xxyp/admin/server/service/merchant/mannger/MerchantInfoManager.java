package com.xxyp.admin.server.service.merchant.mannger;

import java.util.List;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.merchant.MerchantInfo;

public interface MerchantInfoManager {
	public List<MerchantInfo> queryMerchantInfoList(MerchantInfo merchantInfo);
	
	public DataPage<MerchantInfo> queryMerchantInfoList(DataPage<MerchantInfo> page);
	public long saveMerchantInfo(MerchantInfo merchantInfo);
	
	public int updateMerchantInfo(MerchantInfo merchantInfo);
	
	public int deleteMerchantInfo(Long id);
	

}
