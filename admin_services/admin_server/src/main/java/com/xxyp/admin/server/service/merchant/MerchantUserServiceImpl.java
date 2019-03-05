package com.xxyp.admin.server.service.merchant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.ModelResult;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.common.constant.UsedTag;
import com.xxyp.admin.common.common.constant.YesOrNoType;
import com.xxyp.admin.common.model.merchant.MerchantInfo;
import com.xxyp.admin.common.model.merchant.MerchantModule;
import com.xxyp.admin.common.model.merchant.MerchantPermission;
import com.xxyp.admin.common.model.merchant.MerchantPermissionItem;
import com.xxyp.admin.common.model.merchant.MerchantUser;
import com.xxyp.admin.common.model.merchant.constant.MerchantInfoStatus;
import com.xxyp.admin.common.model.merchant.constant.MerchantUserStatus;
import com.xxyp.admin.common.model.merchant.vo.MerchantMenuVo;
import com.xxyp.admin.common.model.merchant.vo.MerchantUserSessionVo;
import com.xxyp.admin.common.service.merchant.MerchantUserService;
import com.xxyp.admin.server.annotation.WriteDataSource;
import com.xxyp.admin.server.service.merchant.mannger.MerchantInfoManager;
import com.xxyp.admin.server.service.merchant.mannger.MerchantModuleManager;
import com.xxyp.admin.server.service.merchant.mannger.MerchantUserManager;

@Service
public class MerchantUserServiceImpl implements MerchantUserService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	public MerchantUserManager merchantUserManager;
	
	@Autowired
	public MerchantInfoManager merchantInfoManager;
	
	@Autowired
	private MerchantModuleManager moduleManager;
	
	//商户管理员角色id
	private Long MerchantRoleId=1L;

	@WriteDataSource
	public List<MerchantUser> queryMerchantUserList(MerchantUser merchantUser) {
		return merchantUserManager.queryMerchantUserList(merchantUser);
	}

	@WriteDataSource
	public PageResult<MerchantUser> queryMerchantUserList(DataPage<MerchantUser> page) {
		page = merchantUserManager.queryMerchantUserList(page);
		PageResult<MerchantUser> result = new PageResult<MerchantUser>();
		result.setPage(page);
		return result;
	}

	@WriteDataSource
	public ModelResult saveMerchantUser(MerchantUser merchantUser) {
		ModelResult result=new ModelResult<>();
		MerchantUser user=new MerchantUser();
		user.setAccount(merchantUser.getAccount());
		List<MerchantUser> userCheck=merchantUserManager.queryMerchantUserList(user);
		if(null!=userCheck&&userCheck.size()>0) {
			result.setErrorMsg("用户名已被注册，请更换");
			return result;
		}
		merchantUserManager.saveMerchantUser(merchantUser);
		return result;
	}
	
	@WriteDataSource
	public int updateMerchantUser(MerchantUser merchantUser) {
		return merchantUserManager.updateMerchantUser(merchantUser);
	}
	
	@WriteDataSource
	public int deleteMerchantUser(Long id) {
		return merchantUserManager.deleteMerchantUser(id);
	}

	@WriteDataSource
	public MerchantUser getMerchantUserById(Long id) {
		if (id == null) {
			return null;
		}
		MerchantUser merchantUser = new MerchantUser();
		merchantUser.setId(id);
		List<MerchantUser> list = queryMerchantUserList(merchantUser);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@WriteDataSource
	public MerchantUser getUserByAccount(String account) {
		return merchantUserManager.getUserByAccount(account);
	}

	@WriteDataSource
	public MerchantUserSessionVo getPermission(MerchantUser user) {
		MerchantUserSessionVo vo = new MerchantUserSessionVo();
		Map<String, MerchantPermission> permissionMap = new HashMap<String, MerchantPermission>();
	    Map<String, MerchantPermissionItem> itemMap = new HashMap<String, MerchantPermissionItem>();
	    Map<Long,MerchantPermission> pMap = new  HashMap<Long,MerchantPermission>();
	    vo.setPermissionMap(permissionMap);
	    vo.setItemMap(itemMap);
	    List<MerchantPermission> permissionList=merchantUserManager.getPermissionList(user);
	    for(MerchantPermission permission:permissionList){
	    	//由于页面加载和数据加载是不同的方法，所以将加载数据方法自动配入子权限
	    	if(!StringUtils.isBlank(permission.getLoadDataMethodName())){
	    		String url = StringUtils.trimToEmpty(permission.getLoadDataMethodName());
	    		MerchantPermissionItem item=new MerchantPermissionItem();
	    		item.setPermission(permission);
	    		item.setMethodValue1(url);
	    		itemMap.put(url, item);
	    	}
	    	String url = StringUtils.trimToEmpty(permission.getMenuUrl());
            permissionMap.put(url, permission);
	    }
	    
	    List<MerchantPermissionItem> itemList=merchantUserManager.getPermissionItemList(user);
	    if (itemList != null && !itemList.isEmpty()) {
	           for(MerchantPermissionItem item :itemList){
	        	   if (StringUtils.isNotEmpty(item.getMethodValue1())) {
	                   itemMap.put(item.getMethodValue1(), item);
	               }
	               if (StringUtils.isNotEmpty(item.getMethodValue2())) {
	                   itemMap.put(item.getMethodValue2(), item);
	               }
	           }            
	        }		
		return vo;
	}

	@WriteDataSource
	public void updateUser(MerchantUser user) {
		merchantUserManager.updateUser(user);
		
	}
	
	@WriteDataSource
	public Map<String, List<MerchantMenuVo>> getMenu(Map<String, MerchantPermission> permissionMap) {
		Map<String, List<MerchantMenuVo>> result = new LinkedHashMap<String, List<MerchantMenuVo>>();
		Map<Long, List<MerchantMenuVo>> map = new TreeMap<Long, List<MerchantMenuVo>>();
		MerchantModule module = null;
		MerchantMenuVo menuVo = null;
		Collection<Long> ids = new LinkedList<Long>();
        for(Entry<String, MerchantPermission> entry :permissionMap.entrySet()){
        	if (ids.contains(entry.getValue().getModuleId())) {
        		continue;
        	}
        	ids.add(entry.getValue().getModuleId());
        }
	    Map<Long,MerchantModule> moduleMap = moduleManager.queryModuleMapByIdSet(ids);
	    if(moduleMap!=null && !moduleMap.isEmpty()){
	    	 for(Entry<String, MerchantPermission> entry :permissionMap.entrySet()){
	    		 if(YesOrNoType.no.getIndex()==entry.getValue().getIsMenu()){
	            	  continue;
	              }
	    		  if(StringUtils.isBlank(entry.getValue().getMenuUrl())){
	            	  continue;
	              }
		    	  module = moduleMap.get(entry.getValue().getModuleId());
	              if(!map.containsKey(entry.getValue().getModuleId())){
  					map.put(module.getId(), new ArrayList<MerchantMenuVo>());
  					moduleMap.put(module.getId(), module);
	              } 
	             
	              menuVo = new MerchantMenuVo();
	  			  menuVo.setHref(entry.getValue().getMenuUrl());
	  			  menuVo.setName(entry.getValue().getName());
	  			 
	  			  menuVo.setOrderIndex(entry.getValue().getOrderIndex());
	  			  map.get(entry.getValue().getModuleId()).add(menuVo);
		    }
	    	 
	    	// 排序的module
	    	Map<Integer, MerchantModule> treeModule = new TreeMap<Integer, MerchantModule>();
	    	Set<Long> keySet = map.keySet();
	    	for (Long key : keySet) {
	    		module = moduleMap.get(key);
	    		treeModule.put(module.getOrderIndex(), module);
	    		// 排序模块中的菜单
	    		sortAdminMenuVo(map.get(key));
	    	}
	    	Set<Integer> treeSet = treeModule.keySet();
	    	for (Integer tKey : treeSet) {
	    		module = treeModule.get(tKey);
	    		result.put(module.getName(), map.get(module.getId()));
	    	}
	    	
	    }else{
	    	logger.info("权限模块为空！");
	    }
	   
		return result;
	}
	
	// 排序
	private void sortAdminMenuVo(List<MerchantMenuVo> resultList) {
		if (resultList != null && resultList.size() > 1) {
			for (int index1 = resultList.size() - 1; index1 > 0; index1--) {
				for (int index2 = 0; index2 < index1; index2++) {
					if (resultList.get(index2).getOrderIndex() > resultList.get(index2 + 1).getOrderIndex()) {
						resultList.add(index2, resultList.get(index2 + 1));
						resultList.remove(index2 + 2);
					}
				}
			}
		}
	}

	@WriteDataSource
	@Transactional
	public ModelResult regMerchantUser(@RequestBody MerchantUser merchantUser) {
		ModelResult result=new ModelResult<>();
		MerchantUser user=new MerchantUser();
		user.setAccount(merchantUser.getAccount());
		List<MerchantUser> userCheck=merchantUserManager.queryMerchantUserList(user);
		if(null!=userCheck&&userCheck.size()>0) {
			result.setErrorMsg("用户名已被注册，请更换");
			return result;
		}
		MerchantInfo merchantInfo=merchantUser.getMerchantInfo();
		merchantInfo.setStatus(MerchantInfoStatus.forbidden.getIndex());
		merchantInfo.setUsedTag(UsedTag.enabled.getIndex());
		long merchantId=merchantInfoManager.saveMerchantInfo(merchantInfo);
		merchantUser.setMerchantId(merchantId);
		merchantUser.setStatus(MerchantUserStatus.enabled.getIndex());
		merchantUser.setMerchantRoleId(MerchantRoleId);//默认商户管理员角色
		merchantUser.setUsedTag(UsedTag.enabled.getIndex());
		merchantUserManager.saveMerchantUser(merchantUser);
		return result;
	}
}
