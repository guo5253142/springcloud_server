package com.xxyp.admin.server.controller.merchant;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.ModelResult;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.merchant.MerchantPermission;
import com.xxyp.admin.common.model.merchant.MerchantUser;
import com.xxyp.admin.common.model.merchant.vo.MerchantMenuVo;
import com.xxyp.admin.common.model.merchant.vo.MerchantUserSessionVo;
import com.xxyp.admin.common.service.merchant.MerchantUserService;

/**
 * 商户用户
 */
@RestController
@RequestMapping("/merchantUser")
public class MerchantUserController {

	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MerchantUserService merchantUserService;
	
	@RequestMapping(value="/queryMerchantUser",method = RequestMethod.POST)
	public List<MerchantUser> queryMerchantUserList(@RequestBody MerchantUser merchantUser){
		return merchantUserService.queryMerchantUserList(merchantUser);
	}
	
	@RequestMapping(value="/getMerchantUserById")
	public MerchantUser getMerchantUserById(@RequestParam Long id) {
		return merchantUserService.getMerchantUserById(id);
	}
	
	@RequestMapping(value="/queryMerchantUserList",method = RequestMethod.POST)
	public PageResult<MerchantUser> queryMerchantUserList(@RequestBody DataPage<MerchantUser>  page){
		return merchantUserService.queryMerchantUserList(page);
	}
	
	@RequestMapping(value="/saveMerchantUser",method = RequestMethod.POST)
	public ModelResult saveMerchantUser(@RequestBody MerchantUser merchantUser) {
		ModelResult result=merchantUserService.saveMerchantUser(merchantUser);
		return result;
	}
	
	@RequestMapping(value="/updateMerchantUser",method = RequestMethod.POST)
	public int updateMerchantUser(@RequestBody MerchantUser merchantUser) {
		return merchantUserService.updateMerchantUser(merchantUser);
	}
	
	@RequestMapping(value="/deleteMerchantUser")
	public int deleteMerchantUser(@RequestParam Long id) {
		return merchantUserService.deleteMerchantUser(id);
	}
	
	@RequestMapping("/getUserByAccount")
	public MerchantUser getUserByAccount(HttpServletRequest request, String account) {
		MerchantUser user = this.merchantUserService.getUserByAccount(account);
		return user;
	}
	
	@RequestMapping("/getMenu")
	public Map<String, List<MerchantMenuVo>>  getMenu(HttpServletRequest request, @RequestBody Map<String, MerchantPermission> permissionMap) {
		 return this.merchantUserService.getMenu(permissionMap);
	}
	
	@RequestMapping("/getPermission")
	public MerchantUserSessionVo getPermission(HttpServletRequest request, @RequestBody MerchantUser user) {
		return this.merchantUserService.getPermission(user);
	}
	
	@RequestMapping("/updateUser")
	public void updateUser(HttpServletRequest request, @RequestBody MerchantUser user) {
		 this.merchantUserService.updateUser(user);
	}
	
	/**
	 * 注册商户用户
	 * @param request
	 * @param user
	 */
	@RequestMapping("/regMerchantUser")
	public ModelResult regMerchantUser(HttpServletRequest request, @RequestBody MerchantUser user) {
		 System.out.println(user.getCreateDate()+"========================");
		 return this.merchantUserService.regMerchantUser(user);
	}
	
}
