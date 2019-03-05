package com.xxyp.admin.common.service.merchant;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xxyp.admin.common.common.DataPage;
import com.xxyp.admin.common.common.ModelResult;
import com.xxyp.admin.common.common.PageResult;
import com.xxyp.admin.common.model.merchant.MerchantPermission;
import com.xxyp.admin.common.model.merchant.MerchantUser;
import com.xxyp.admin.common.model.merchant.vo.MerchantMenuVo;
import com.xxyp.admin.common.model.merchant.vo.MerchantUserSessionVo;

public interface MerchantUserService{
	
	@RequestMapping(value="/queryMerchantUser",method = RequestMethod.POST)
	public List<MerchantUser> queryMerchantUserList(@RequestBody MerchantUser merchantUser);
	
	@RequestMapping(value="/getMerchantUserById")
	public MerchantUser getMerchantUserById(@RequestParam("id") Long id);
	
	@RequestMapping(value="/queryMerchantUserList",method = RequestMethod.POST)
	public PageResult<MerchantUser> queryMerchantUserList(@RequestBody DataPage<MerchantUser>  page);
	
	@RequestMapping(value="/saveMerchantUser",method = RequestMethod.POST)
	public ModelResult saveMerchantUser(@RequestBody MerchantUser merchantUser);
	
	@RequestMapping(value="/updateMerchantUser",method = RequestMethod.POST)
	public int updateMerchantUser(@RequestBody MerchantUser merchantUser);
	
	@RequestMapping(value="/deleteMerchantUser")
	public int deleteMerchantUser(@RequestParam("id") Long id);
	
	/**
	 * 查询用户信息
	 * @param account 账号
	 * @return
	 */
	@RequestMapping(value="/getUserByAccount" )
	public MerchantUser getUserByAccount(@RequestParam("account") String account);
	
	/**
	 * 查询权限
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/getPermission",method = RequestMethod.POST)
	public  MerchantUserSessionVo getPermission(@RequestBody  MerchantUser user);
	
	/**
	 * 修改user数据
	 * @param user
	 */
	@RequestMapping(value="/updateUser",method = RequestMethod.POST )
	public void updateUser(@RequestBody MerchantUser user);
	
	/**
	 * 根据权限获取菜单
	 * @param permissionMap
	 * @return
	 */
	@RequestMapping(value="/getMenu",method = RequestMethod.POST )
	public Map<String,List<MerchantMenuVo>> getMenu(@RequestBody Map<String, MerchantPermission> permissionMap);
	
	/**
	 * 注册商户用户
	 * @param permissionMap
	 * @return
	 */
	@RequestMapping(value="/regMerchantUser",method = RequestMethod.POST )
	public ModelResult regMerchantUser(@RequestBody MerchantUser merchantUser);
	
	
	
}
