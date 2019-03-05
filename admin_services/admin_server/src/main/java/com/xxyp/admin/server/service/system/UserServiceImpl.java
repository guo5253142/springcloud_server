package com.xxyp.admin.server.service.system;

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

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.common.constant.YesOrNoType;
import com.xxyp.admin.common.model.system.Module;
import com.xxyp.admin.common.model.system.Permission;
import com.xxyp.admin.common.model.system.PermissionItem;
import com.xxyp.admin.common.model.system.User;
import com.xxyp.admin.common.model.system.vo.AdminMenuVo;
import com.xxyp.admin.common.model.system.vo.UserSessionVo;
import com.xxyp.admin.common.service.system.UserService;
import com.xxyp.admin.server.annotation.WriteDataSource;
import com.xxyp.admin.server.service.system.mannger.ModuleManager;
import com.xxyp.admin.server.service.system.mannger.UserManager;

@Service
public class UserServiceImpl implements UserService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public UserManager userManager;
	
	@Autowired
	private ModuleManager moduleManager;

	@WriteDataSource
	public User getUserByAccount(String account) {
		return userManager.getUserByAccount(account);
	}


	@WriteDataSource
	public UserSessionVo getPermission(User user) {
		UserSessionVo vo = new UserSessionVo();
		Map<String, Permission> permissionMap = new HashMap<String, Permission>();
	    Map<String, PermissionItem> itemMap = new HashMap<String, PermissionItem>();
	    Map<Long,Permission> pMap = new  HashMap<Long,Permission>();
	    vo.setPermissionMap(permissionMap);
	    vo.setItemMap(itemMap);
	    List<Permission> permissionList=userManager.getPermissionList(user);
	    for(Permission permission:permissionList){
	    	//由于页面加载和数据加载是不同的方法，所以将加载数据方法自动配入子权限
	    	if(!StringUtils.isBlank(permission.getLoadDataMethodName())){
	    		String url = StringUtils.trimToEmpty(permission.getLoadDataMethodName());
	    		PermissionItem item=new PermissionItem();
	    		item.setPermission(permission);
	    		item.setMethodValue1(url);
	    		itemMap.put(url, item);
	    	}
	    	String url = StringUtils.trimToEmpty(permission.getMenuUrl());
            permissionMap.put(url, permission);
	    }
	    
	    List<PermissionItem> itemList=userManager.getPermissionItemList(user);
	    if (itemList != null && !itemList.isEmpty()) {
	           for(PermissionItem item :itemList){
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
	public void updateUser(User user) {
		userManager.updateUser(user);
	}


	@WriteDataSource
	public void saveEditUser(User user) {
		userManager.saveEditUser(user);
	}


	@WriteDataSource
	public Map<String, List<AdminMenuVo>> getMenu(Map<String, Permission> permissionMap) {
		Map<String, List<AdminMenuVo>> result = new LinkedHashMap<String, List<AdminMenuVo>>();
		Map<Long, List<AdminMenuVo>> map = new TreeMap<Long, List<AdminMenuVo>>();
		Module module = null;
		AdminMenuVo menuVo = null;
		Collection<Long> ids = new LinkedList<Long>();
        for(Entry<String, Permission> entry :permissionMap.entrySet()){
        	if (ids.contains(entry.getValue().getModuleId())) {
        		continue;
        	}
        	ids.add(entry.getValue().getModuleId());
        }
	    Map<Long,Module> moduleMap = moduleManager.queryModuleMapByIdSet(ids);;
	    if(moduleMap!=null && !moduleMap.isEmpty()){
	    	 for(Entry<String, Permission> entry :permissionMap.entrySet()){
	    		 if(YesOrNoType.no.getIndex()==entry.getValue().getIsMenu()){
	            	  continue;
	              }
	    		  if(StringUtils.isBlank(entry.getValue().getMenuUrl())){
	            	  continue;
	              }
		    	  module = moduleMap.get(entry.getValue().getModuleId());
	              if(!map.containsKey(entry.getValue().getModuleId())){
  					map.put(module.getId(), new ArrayList<AdminMenuVo>());
  					moduleMap.put(module.getId(), module);
	              } 
	             
	              menuVo = new AdminMenuVo();
	  			  menuVo.setHref(entry.getValue().getMenuUrl());
	  			  menuVo.setName(entry.getValue().getName());
	  			 
	  			  menuVo.setOrderIndex(entry.getValue().getOrderIndex());
	  			  map.get(entry.getValue().getModuleId()).add(menuVo);
		    }
	    	 
	    	// 排序的module
	    	Map<Integer, Module> treeModule = new TreeMap<Integer, Module>();
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
			private void sortAdminMenuVo(List<AdminMenuVo> resultList) {
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
	public void saveUser(User user) {
		userManager.saveUser(user);
	}


	@WriteDataSource
	public void deleteUser(User user) {
		userManager.deleteUser(user);
	}

	@WriteDataSource
	public User getUser(User user) {
		return userManager.getUser(user);
	}

	@WriteDataSource
	public PageResult<User> queryUser(DataPage<User> page) {
		page = userManager.queryUser(page);
		PageResult<User> result = new PageResult<User>();
		result.setPage(page);
		return result;
	}

}