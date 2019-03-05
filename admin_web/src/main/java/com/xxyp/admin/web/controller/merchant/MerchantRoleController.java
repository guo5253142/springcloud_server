package com.xxyp.admin.web.controller.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xxyp.admin.client.service.client.admin.merchant.MerchantModuleServiceClient;
import com.xxyp.admin.client.service.client.admin.merchant.MerchantPermissionItemServiceClient;
import com.xxyp.admin.client.service.client.admin.merchant.MerchantPermissionServiceClient;
import com.xxyp.admin.client.service.client.admin.merchant.MerchantRolePermissionServiceClient;
import com.xxyp.admin.client.service.client.admin.merchant.MerchantRoleServiceClient;
import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.merchant.MerchantModule;
import com.xxyp.admin.common.model.merchant.MerchantPermission;
import com.xxyp.admin.common.model.merchant.MerchantPermissionItem;
import com.xxyp.admin.common.model.merchant.MerchantRole;
import com.xxyp.admin.common.model.merchant.MerchantRolePermission;
import com.xxyp.admin.web.controller.BaseController;

@Controller
@RequestMapping("/merchant/role")
public class MerchantRoleController extends BaseController<MerchantRole> {

	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MerchantRoleServiceClient roleService;
	@Autowired
	private MerchantModuleServiceClient moduleService;
	@Autowired
	private MerchantPermissionServiceClient permissionService;
	@Autowired
	private MerchantPermissionItemServiceClient permissionItemService;
	@Autowired
	private MerchantRolePermissionServiceClient rolePermissionService;
	

	@RequestMapping("/init")
	public ModelAndView init(ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/merchant/merchantRole/role");
		return mv;
	}
	@RequestMapping("/listRole")
	public void listRole(MerchantRole vo,HttpServletResponse response, DataPage<MerchantRole> page) {
		page.setDataObj(vo);
		PageResult<MerchantRole> result = roleService.queryRoleList(page);
		returnPageJson(response,result.getPage());
	}

	@RequestMapping("/addRole")
	public ModelAndView addRole(ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/merchant/merchantRole/addRole");
		List<MerchantModule> moduleList = moduleService.queryModuleList(new MerchantModule());
		Map<Long, List<MerchantPermission>> modulePermissionMap = new HashMap<Long, List<MerchantPermission>>();
		List<MerchantPermission> permissionList = permissionService.queryPermission(new MerchantPermission());
		for (MerchantPermission permission : permissionList) {
			List<MerchantPermission> lp = modulePermissionMap.get(permission.getModuleId());
			if (lp == null) {
				lp = new ArrayList<MerchantPermission>();
				lp.add(permission);
				modulePermissionMap.put(permission.getModuleId(), lp);
				continue;
			}
			lp.add(permission);
		}
		Map<Long, List<MerchantPermissionItem>> permissionAndItemMap = new HashMap<Long, List<MerchantPermissionItem>>();
		List<MerchantPermissionItem> permissionItemList = permissionItemService.queryPermissionItemList(new MerchantPermissionItem());
		for (MerchantPermissionItem permissionItem : permissionItemList) {
			List<MerchantPermissionItem> pi = permissionAndItemMap.get(permissionItem.getPermissionId());
			if (pi == null) {
				pi = new ArrayList<MerchantPermissionItem>();
				pi.add(permissionItem);
				permissionAndItemMap.put(permissionItem.getPermissionId(), pi);
				continue;
			}
			pi.add(permissionItem);
		}

		modelMap.put("moduleList", moduleList);
		modelMap.put("modulePermissionMap", modulePermissionMap);
		modelMap.put("permissionAndItemMap", permissionAndItemMap);
		return mv;
	}

	@RequestMapping("/saveRole")
	public void saveRole(HttpServletRequest request,HttpServletResponse response,MerchantRole role,String[] permissionItem,
			String[] permissionIds) {
		try {
			List<MerchantRole> roleList = roleService.queryRoleList(role);
			if (roleList != null && roleList.size() > 0) {
				outJson(response,this.AJAX_FAIL,"该角色名已存在");
				return;
			}
			long rid = roleService.saveRole(role);
			if (rid != 0) {
				MerchantRolePermission rolePermission = null;
				String[] permissionIdAndItemId = null;
				if (permissionItem != null) {
					for (String str : permissionItem) {
						permissionIdAndItemId = str.split("_");
						rolePermission = new MerchantRolePermission();
						rolePermission.setPermissionId(Long.valueOf(permissionIdAndItemId[0]));
						rolePermission.setPermissionItemId(Long.valueOf(permissionIdAndItemId[1]));
						rolePermission.setRoleId(rid);
						rolePermissionService.saveRolePermission(rolePermission);
					}
				}
				if (permissionIds != null) {
					for (String pid : permissionIds) {
						rolePermission = new MerchantRolePermission();
						rolePermission.setPermissionId(Long.valueOf(pid));
						rolePermission.setRoleId(rid);
						rolePermissionService.saveRolePermission(rolePermission);
					}
				}
				outJson(response,this.AJAX_SUCCESS,"添加角色成功");
				return;
			}
			outJson(response,this.AJAX_FAIL,"操作失败，请稍后再试！");
		} catch (NumberFormatException e) {
			logger.error("saveRole 异常",e);
			outJsonForException(response);
		}
	}

	@RequestMapping("/editRole")
	public ModelAndView editRole(Long id, ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/merchant/merchantRole/editRole");
		MerchantRole role = roleService.getRoleById(id);
		List<MerchantModule> moduleList = moduleService.queryModuleList(new MerchantModule());
		Map<Long, List<MerchantPermission>> modulePermissionMap = new HashMap<Long, List<MerchantPermission>>();
		// 查询角色已有权限
		List<MerchantRolePermission> rolePermission = rolePermissionService
				.queryRolePermissionListByRoleId(id);
		List<Long> pIdList = new ArrayList<Long>();
		List<Long> pItemIdList = new ArrayList<Long>();
		for (MerchantRolePermission rp : rolePermission) {
			if (rp.getPermissionId() != null) {
				if (!pIdList.contains(rp.getPermissionId())) {
					pIdList.add(rp.getPermissionId());
				}
			}
			if (rp.getPermissionItemId() != null) {
				if (!pItemIdList.contains(rp.getPermissionItemId())) {
					pItemIdList.add(rp.getPermissionItemId());
				}
			}
		}

		List<MerchantPermission> permissionList = permissionService.queryPermission(new MerchantPermission());
		for (MerchantPermission permission : permissionList) {
			List<MerchantPermission> lp = modulePermissionMap.get(permission.getModuleId());
			if (pIdList.contains(permission.getId())) {
				permission.setIsChecked(true);
			}
			if (lp == null) {
				lp = new ArrayList<MerchantPermission>();
				lp.add(permission);
				modulePermissionMap.put(permission.getModuleId(), lp);
				continue;
			}
			lp.add(permission);
		}
		Map<Long, List<MerchantPermissionItem>> permissionAndItemMap = new HashMap<Long, List<MerchantPermissionItem>>();
		List<MerchantPermissionItem> permissionItemList = permissionItemService
				.queryPermissionItemList(new MerchantPermissionItem());
		for (MerchantPermissionItem permissionItem : permissionItemList) {
			List<MerchantPermissionItem> pi = permissionAndItemMap.get(permissionItem.getPermissionId());
			if (pItemIdList.contains(permissionItem.getId())) {
				permissionItem.setIsChecked(true);
			}
			if (pi == null) {
				pi = new ArrayList<MerchantPermissionItem>();
				pi.add(permissionItem);
				permissionAndItemMap.put(permissionItem.getPermissionId(), pi);
				continue;
			}
			pi.add(permissionItem);
		}

		modelMap.put("moduleList", moduleList);
		modelMap.put("modulePermissionMap", modulePermissionMap);
		modelMap.put("permissionAndItemMap", permissionAndItemMap);
		modelMap.put("role", role);
		return mv;
	}

	@RequestMapping("/updateRole")
	public void updateRole(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap, MerchantRole role, String[] permissionItem,
			String[] permissionIds) {
		try {
			MerchantRole r = new MerchantRole();
			r.setName(role.getName());
			List<MerchantRole> roleList = roleService.queryRoleList(r);
			if (roleList != null && roleList.size() > 0
					&& roleList.get(0).getId().longValue()!=role.getId().longValue()) {
				outJson(response,this.AJAX_FAIL,"该角色名已存在");
				return;
			}
			if (roleService.updateRole(role) != 0) {
				MerchantRolePermission rolePermission = null;
				MerchantRolePermission rPermission = new MerchantRolePermission();
				// 删除旧数据
				rPermission.setRoleId(role.getId());
				rolePermissionService.deleteRolePermission(rPermission);
				// 添加新数据
				if (permissionItem != null) {
					String[] moduleIdpermissionIdAndItemId = null;
					for (String str : permissionItem) {
						moduleIdpermissionIdAndItemId = str.split("_");
						rolePermission = new MerchantRolePermission();
						rolePermission.setPermissionId(Long.valueOf(moduleIdpermissionIdAndItemId[0]));
						rolePermission.setPermissionItemId(Long
								.valueOf(moduleIdpermissionIdAndItemId[1]));
						rolePermission.setRoleId(role.getId());
						rolePermissionService.saveRolePermission(rolePermission);
					}
				}
				if (permissionIds != null) {
					for (String pid : permissionIds) {
						rolePermission = new MerchantRolePermission();
						rolePermission.setPermissionId(Long.valueOf(pid));
						rolePermission.setRoleId(role.getId());
						rolePermissionService.saveRolePermission(rolePermission);
					}
				}
				outJson(response,this.AJAX_SUCCESS,"修改角色成功");
				return;
			}
			outJson(response,this.AJAX_FAIL,"操作失败，请稍后再试！");
		} catch (NumberFormatException e) {
			logger.error("updateRole 异常",e);
			outJsonForException(response);
		}
	}

	@RequestMapping("/deleteRole")
	public void deleteRole(HttpServletResponse response,Long id) {
		try {
			if(id.longValue()!=this.ADMIN_ROLE_ID){
				// 删除item
				MerchantRolePermission rolePermission = new MerchantRolePermission();
				rolePermission.setRoleId(id);
				rolePermissionService.deleteRolePermission(rolePermission);
				roleService.deleteRole(id);
				outJson(response,this.AJAX_SUCCESS,"删除角色成功");
			}else{
				outJson(response,this.AJAX_FAIL,"无法删除系统管理员角色");
			}
		} catch (Exception e) {
			logger.error("deleteRole 异常",e);
			outJsonForException(response);
		}
		
	}
	
}
