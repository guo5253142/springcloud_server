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

import com.xxyp.admin.client.service.client.admin.merchant.MerchantInfoServiceClient;
import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.common.constant.UsedTag;
import com.xxyp.admin.common.model.merchant.constant.MerchantInfoStatus;
import com.xxyp.admin.common.model.merchant.MerchantInfo;
import com.xxyp.admin.web.controller.BaseController;

/**
 * 商户信息
 */
@Controller
@RequestMapping("/merchant/merchantInfo")
public class MerchantInfoController extends BaseController<MerchantInfo>{
	
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private MerchantInfoServiceClient merchantInfoServiceClient;
	
	@RequestMapping("/init")
	public ModelAndView init(ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/merchant/merchantInfo/merchantInfo");
		List<MerchantInfoStatus> status = MerchantInfoStatus.getAll(MerchantInfoStatus.class);
		modelMap.put("status", status);
		return mv;
	}
	@RequestMapping("/listMerchantInfo")
	public void listMerchantInfo(MerchantInfo vo,HttpServletResponse response, DataPage<MerchantInfo> page) {
		page.setDataObj(vo);
		PageResult<MerchantInfo> result = merchantInfoServiceClient.queryMerchantInfoList(page);
		returnPageJson(response,result.getPage());
	}

	@RequestMapping("/addMerchantInfo")
	public ModelAndView addMerchantInfo(ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/merchant/merchantInfo/addMerchantInfo");
		List<MerchantInfoStatus> status = MerchantInfoStatus.getAll(MerchantInfoStatus.class);
		modelMap.put("status", status);
		return mv;
	}

	@RequestMapping("/saveMerchantInfo")
	public void saveMerchantInfo(HttpServletRequest request,HttpServletResponse response,MerchantInfo merchantInfo) {
		try {
			merchantInfo.setUsedTag(UsedTag.enabled.getIndex());
			merchantInfo.setCreator(getUser(request).getUser().getId());
			merchantInfo.setCreateDate(new Date());
			merchantInfoServiceClient.saveMerchantInfo(merchantInfo);
			outJson(response,this.AJAX_SUCCESS,"添加成功");
		} catch (Exception e) {
			logger.error("saveMerchantInfo 异常",e);
			outJsonForException(response);
		}
	}

	@RequestMapping("/editMerchantInfo")
	public ModelAndView editMerchantInfo(Long id, ModelMap modelMap) {
		ModelAndView mv = new ModelAndView("page/merchant/merchantInfo/editMerchantInfo");
		List<MerchantInfoStatus> status = MerchantInfoStatus.getAll(MerchantInfoStatus.class);
		modelMap.put("status", status);
		MerchantInfo merchantInfo = merchantInfoServiceClient.getMerchantInfoById(id);
		modelMap.put("merchantInfo", merchantInfo);
		return mv;
	}

	@RequestMapping("/updateMerchantInfo")
	public void updateMerchantInfo(HttpServletRequest request,HttpServletResponse response,MerchantInfo merchantInfo) {
		try {
			merchantInfo.setEditor(getUser(request).getUser().getId());
			merchantInfo.setEditDate(new Date());
			merchantInfoServiceClient.updateMerchantInfo(merchantInfo);
			outJson(response,this.AJAX_SUCCESS,"更新成功");
		} catch (Exception e) {
			logger.error("updateMerchantInfo 异常",e);
			outJsonForException(response);
		}
	}

	@RequestMapping("/deleteMerchantInfo")
	public void deleteMerchantInfo(HttpServletResponse response,Long id) {
		try {
			merchantInfoServiceClient.deleteMerchantInfo(id);
			outJson(response,this.AJAX_SUCCESS,"删除成功");
		} catch (Exception e) {
			logger.error("deleteUser 异常",e);
			outJsonForException(response);
		}
		
	}
	
	
}
