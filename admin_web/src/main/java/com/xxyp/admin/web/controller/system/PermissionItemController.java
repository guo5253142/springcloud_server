package com.xxyp.admin.web.controller.system;


import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xxyp.admin.client.service.client.admin.system.PermissionItemServiceClient;
import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.model.system.PermissionItem;
import com.xxyp.admin.web.controller.BaseController;


@Controller
@RequestMapping("/system/permissionItem")
public class PermissionItemController extends BaseController<PermissionItem> {
	
	@Autowired
	private PermissionItemServiceClient permissionItemService;
	
	@RequestMapping("/init")
	public ModelAndView init(ModelMap modelMap,Long pid) {
		ModelAndView mv = new ModelAndView("page/system/permissionItem/permissionItem");
		modelMap.put("pid", pid);
		return mv;
	}
	
	@RequestMapping("/listPermissionItem")
	public void listPermissionItem(HttpServletResponse response,DataPage<PermissionItem> page,Long pid) {
		PermissionItem pItem = new PermissionItem();
		pItem.setPermissionId(pid);
		List<PermissionItem> permissionItemList = permissionItemService.queryPermissionItemList(pItem);
		page.setDataList(permissionItemList);
		page.setTotalCount(permissionItemList.size());
		returnPageJson(response,page);
	}
	
	@RequestMapping("/addPermissionItem")
	public ModelAndView addPermissionItem(ModelMap modelMap, Long pid) {
		ModelAndView mv = new ModelAndView("page/system/permissionItem/addPermissionItem");
		modelMap.put("pid", pid);
		return mv;
	}
	
	@RequestMapping("/savePermissionItem")
	public void savePermissionItem(HttpServletResponse response,PermissionItem permissionItem) {
		try {
			if (StringUtils.isNotBlank(permissionItem.getName())) {
				PermissionItem item = new PermissionItem();
				item.setName(permissionItem.getName());
				item.setPermissionId(permissionItem.getPermissionId());
				List<PermissionItem> itemList = permissionItemService.queryPermissionItemList(item);
				if (itemList != null && itemList.size() > 0) {
					outJson(response,this.AJAX_FAIL,"子权限已存在");
					return;
				}
			}
			if (permissionItemService.savePermissionItem(permissionItem) != 0) {
				outJson(response,this.AJAX_SUCCESS,"新增成功");
				return;
			}
		} catch (Exception e) {
			logger.error("savePermissionItem 异常",e);
			outJsonForException(response);
		}
	}
	
	@RequestMapping("/editPermissionItem")
	public ModelAndView editPermissionItem(ModelMap modelMap, Long id) {
		ModelAndView mv = new ModelAndView("page/system/permissionItem/editPermissionItem");
		PermissionItem permissionItem = permissionItemService.getPermissionItemById(id);
		
		modelMap.put("permissionItem", permissionItem);
		return mv;
	}
	
	@RequestMapping("/updatePermissionItem")
	public void updatePermissionItem(HttpServletResponse response,PermissionItem permissionItem) {
		try {
			if (StringUtils.isNotBlank(permissionItem.getName())) {
				PermissionItem item = new PermissionItem();
				item.setName(permissionItem.getName());
				item.setPermissionId(permissionItem.getPermissionId());
				List<PermissionItem> itemList = permissionItemService.queryPermissionItemList(item);
				if (itemList != null && itemList.size() > 0 
						&& itemList.get(0).getId().intValue() != permissionItem.getId().intValue()) {
					outJson(response,this.AJAX_FAIL,"子权限已存在");
					return;
				}
			}
			if (permissionItemService.updatePermissionItem(permissionItem) != 0) {
				outJson(response,this.AJAX_SUCCESS,"修改成功");
				return;
			}
		} catch (Exception e) {
			logger.error("updatePermissionItem 异常",e);
			outJsonForException(response);
		}
	}
	
	@RequestMapping("/deletePermissionItem")
	public void delPermissionItem(HttpServletResponse response,String ids) {
		try {
			List<String> idList=Arrays.asList(ids.split(","));
			if (permissionItemService.delPermissionItemByIds(idList) != 0) {
				outJson(response,this.AJAX_SUCCESS,"删除成功");
				return;
			}
		} catch (Exception e) {
			logger.error("delPermissionItem 异常",e);
			outJsonForException(response);
		}
	}
}
