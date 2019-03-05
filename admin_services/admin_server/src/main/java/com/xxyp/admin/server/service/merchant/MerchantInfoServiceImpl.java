package com.xxyp.admin.server.service.merchant;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.merchant.MerchantInfo;
import com.xxyp.admin.common.service.merchant.MerchantInfoService;
import com.xxyp.admin.server.annotation.WriteDataSource;
import com.xxyp.admin.server.service.merchant.mannger.MerchantInfoManager;

@Service
public class MerchantInfoServiceImpl implements MerchantInfoService {
	@Autowired
	public MerchantInfoManager merchantInfoManager;

	@WriteDataSource
	public List<MerchantInfo> queryMerchantInfoList(MerchantInfo merchantInfo) {
		return merchantInfoManager.queryMerchantInfoList(merchantInfo);
	}

	@WriteDataSource
	public PageResult<MerchantInfo> queryMerchantInfoList(DataPage<MerchantInfo> page) {
		page = merchantInfoManager.queryMerchantInfoList(page);
		PageResult<MerchantInfo> result = new PageResult<MerchantInfo>();
		result.setPage(page);
		return result;
	}

	@WriteDataSource
	public long saveMerchantInfo(MerchantInfo merchantInfo) {
		return merchantInfoManager.saveMerchantInfo(merchantInfo);
	}
	
	@WriteDataSource
	public int updateMerchantInfo(MerchantInfo merchantInfo) {
		return merchantInfoManager.updateMerchantInfo(merchantInfo);
	}
	
	@WriteDataSource
	public int deleteMerchantInfo(Long id) {
		return merchantInfoManager.deleteMerchantInfo(id);
	}

	@WriteDataSource
	public MerchantInfo getMerchantInfoById(Long id) {
		if (id == null) {
			return null;
		}
		MerchantInfo merchantInfo = new MerchantInfo();
		merchantInfo.setId(id);
		List<MerchantInfo> list = queryMerchantInfoList(merchantInfo);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	
}
