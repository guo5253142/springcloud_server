package com.xxyp.admin.web.controller.merchant;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xxyp.admin.client.service.client.admin.merchant.MerchantRoleServiceClient;
import com.xxyp.admin.client.service.client.admin.merchant.MerchantUserServiceClient;
import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.ModelResult;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.common.constant.UsedTag;
import com.xxyp.admin.common.model.merchant.MerchantRole;
import com.xxyp.admin.common.model.merchant.MerchantUser;
import com.xxyp.admin.common.model.merchant.constant.MerchantUserStatus;
import com.xxyp.admin.common.util.MD5;
import com.xxyp.admin.web.controller.BaseController;

/**
 * 商户用户
 */
@Controller
@RequestMapping("/merchant/merchantUser")
public class MerchantUserController extends BaseController<MerchantUser>{
	
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private MerchantUserServiceClient merchantUserServiceClient;
	
	@Resource
	private MerchantRoleServiceClient RoleServiceClient;
	
	@RequestMapping("/init")
	public ModelAndView init(ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/merchant/merchantUser/merchantUser");
		List<MerchantUserStatus> status = MerchantUserStatus.getAll(MerchantUserStatus.class);
		List<MerchantRole> roleList = RoleServiceClient.queryRoleList(new MerchantRole());
		modelMap.put("roleList", roleList);
		modelMap.put("status", status);
		return mv;
	}
	@RequestMapping("/listMerchantUser")
	public void listMerchantUser(MerchantUser vo,HttpServletResponse response, DataPage<MerchantUser> page) {
		page.setDataObj(vo);
		PageResult<MerchantUser> result = merchantUserServiceClient.queryMerchantUserList(page);
		returnPageJson(response,result.getPage());
	}

	@RequestMapping("/addMerchantUser")
	public ModelAndView addMerchantUser(ModelMap modelMap,Long merchantId) {
		ModelAndView mv = new ModelAndView("page/merchant/merchantUser/addMerchantUser");
		List<MerchantUserStatus> status = MerchantUserStatus.getAll(MerchantUserStatus.class);
		List<MerchantRole> roleList = RoleServiceClient.queryRoleList(new MerchantRole());
		modelMap.put("roleList", roleList);
		modelMap.put("status", status);
		modelMap.put("merchantId", merchantId);
		return mv;
	}

	@RequestMapping("/saveMerchantUser")
	public void saveMerchantUser(HttpServletRequest request,HttpServletResponse response,MerchantUser merchantUser) {
		try {
			merchantUser.setUsedTag(UsedTag.enabled.getIndex());
			merchantUser.setPassword((MD5.encode1(merchantUser.getPassword())));
			merchantUser.setCreator(getUser(request).getUser().getId());
			merchantUser.setCreateDate(new Date());
			ModelResult result=merchantUserServiceClient.saveMerchantUser(merchantUser);
			if(result.getSuccessFlag()) {
				outJson(response,this.AJAX_SUCCESS,"添加成功");
			}else {
				outJson(response,this.AJAX_FAIL,result.getErrorMsg());
			}
			
		} catch (Exception e) {
			logger.error("saveMerchantUser 异常",e);
			outJsonForException(response);
		}
	}

	@RequestMapping("/editMerchantUser")
	public ModelAndView editMerchantUser(Long id, ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/merchant/merchantUser/editMerchantUser");
		List<MerchantUserStatus> status = MerchantUserStatus.getAll(MerchantUserStatus.class);
		List<MerchantRole> roleList = RoleServiceClient.queryRoleList(new MerchantRole());
		modelMap.put("roleList", roleList);
		modelMap.put("status", status);
		MerchantUser merchantUser = merchantUserServiceClient.getMerchantUserById(id);
		modelMap.put("merchantUser", merchantUser);
		return mv;
	}

	@RequestMapping("/updateMerchantUser")
	public void updateMerchantUser(HttpServletRequest request,HttpServletResponse response,MerchantUser merchantUser) {
		try {
			merchantUser.setEditor(getUser(request).getUser().getId());
			merchantUser.setEditDate(new Date());
			merchantUserServiceClient.updateMerchantUser(merchantUser);
			outJson(response,this.AJAX_SUCCESS,"更新成功");
		} catch (Exception e) {
			logger.error("updateMerchantUser 异常",e);
			outJsonForException(response);
		}
	}

	@RequestMapping("/deleteMerchantUser")
	public void deleteMerchantUser(HttpServletResponse response,Long id) {
		try {
			merchantUserServiceClient.deleteMerchantUser(id);
			outJson(response,this.AJAX_SUCCESS,"删除成功");
		} catch (Exception e) {
			logger.error("deleteUser 异常",e);
			outJsonForException(response);
		}
		
	}
	
	
}
