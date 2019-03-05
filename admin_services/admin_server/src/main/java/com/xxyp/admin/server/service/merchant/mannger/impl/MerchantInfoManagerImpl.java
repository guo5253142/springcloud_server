package com.xxyp.admin.server.service.merchant.mannger.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.merchant.MerchantInfo;
import com.xxyp.admin.common.util.MapUtil;
import com.xxyp.admin.server.dao.GenericMybatisDao;
import com.xxyp.admin.server.service.merchant.mannger.MerchantInfoManager;
@Component
public class MerchantInfoManagerImpl implements MerchantInfoManager{
	@Autowired
	private GenericMybatisDao adminDbDao;

	public List<MerchantInfo> queryMerchantInfoList(MerchantInfo merchantInfo) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(merchantInfo);
		return adminDbDao.queryList("MerchantInfoMapper.getMerchantInfo", paramMap);
	}
	
	public DataPage<MerchantInfo> queryMerchantInfoList(DataPage<MerchantInfo> page) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(page.getDataObj());
		return adminDbDao.queryPage("MerchantInfoMapper.countMerchantInfoForPage", "MerchantInfoMapper.queryMerchantInfoForPage", paramMap, page);
	}
	
	public long saveMerchantInfo(MerchantInfo merchantInfo) {
		adminDbDao.insertAndSetupId("MerchantInfoMapper.saveMerchantInfo", merchantInfo);
		return merchantInfo.getId();
	}
	
	public int updateMerchantInfo(MerchantInfo merchantInfo) {
		return adminDbDao.updateByObj("MerchantInfoMapper.updateMerchantInfo", merchantInfo);
	}
	
	public int deleteMerchantInfo(Long id) {
		if (id == null) {
			return 0;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return adminDbDao.update("MerchantInfoMapper.deleteMerchantInfo", paramMap);
	}
	
	
}
