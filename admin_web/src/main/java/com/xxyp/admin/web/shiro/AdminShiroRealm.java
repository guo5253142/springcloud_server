package com.xxyp.admin.web.shiro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxyp.admin.client.service.client.admin.system.UserServiceClient;
import com.xxyp.admin.common.common.constant.YesOrNoType;
import com.xxyp.admin.common.model.system.Permission;
import com.xxyp.admin.common.model.system.PermissionItem;
import com.xxyp.admin.common.model.system.User;
import com.xxyp.admin.common.model.system.vo.UserSessionVo;
import com.xxyp.admin.common.util.MD5;
import com.xxyp.admin.common.util.StringUtil;
import com.xxyp.admin.web.common.constant.Constants;

public class AdminShiroRealm extends AuthorizingRealm{
	
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private UserServiceClient userServiceClient;

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("---------------- 执行 Shiro 权限获取 ---------------------");
		Subject subject = SecurityUtils.getSubject();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		User user = (User)  principals.getPrimaryPrincipal();  //取得登录用户
		
		UserSessionVo vo = userServiceClient.getPermission(user);
		subject.getSession().setAttribute(Constants.USER_LOGIN_SESSION, vo);
		List<String> permissions = new ArrayList<String>(); 
		for(Entry<String, Permission> entry :vo.getPermissionMap().entrySet()){
			if(!StringUtil.isBlank(entry.getValue().getMenuUrl())) {
				permissions.add(entry.getValue().getMenuUrl());
			}
			
		}
		for(Entry<String, PermissionItem> entry :vo.getItemMap().entrySet()){
			PermissionItem item=entry.getValue();
			if(!StringUtil.isBlank(item.getMethodValue1())) {
				permissions.add(entry.getValue().getMethodValue1());
			}
			if(!StringUtil.isBlank(item.getMethodValue2())) {
				permissions.add(entry.getValue().getMethodValue2());
			}
		}
		info.addStringPermissions(permissions); 
		return info;
       
	}

	/**
	 * 认证回调函数,登录时调用.
	 */
	/*@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.info("---------------- 执行 Shiro 凭证认证 ----------------------");
		//token中储存着输入的用户名和密码
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		//获得用户名与密码
		String account = upToken.getUsername();
		String password = String.valueOf(upToken.getPassword());
		User user = userServiceClient.getUserByAccount(account);
		
		//用户不存在
		if (user==null){
			throw new UnknownAccountException();
		}
		if (user.getStatus()==YesOrNoType.no.getIndex()) {
			//账号禁用 ，访问受限
			throw new DisabledAccountException();
		}
		//验正密码
		if (!user.getPassword().equals(MD5.encode1(password))) {
			throw new IncorrectCredentialsException();
		}else {
			logger.info("---------------- Shiro 凭证认证成功 ----------------------："+account);
			user.setLastLoginTime(new Date(System.currentTimeMillis()));
			userServiceClient.updateUser(user);
			
			//获取权限
			Subject subject = SecurityUtils.getSubject();
			UserSessionVo vo = userServiceClient.getPermission(user);
			vo.setUser(user);
			subject.getSession().setAttribute(Constants.USER_LOGIN_SESSION, vo);
			
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password,getName());
			return info;
		}
		
	}*/
	
	/**
	 *  直接查询权限
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.info("---------------- 执行 Shiro 凭证认证 ,查询权限----------------------");
		//token中储存着输入的用户名和密码
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		//获得用户名与密码
		String account = upToken.getUsername();
		User user = userServiceClient.getUserByAccount(account);
	/*	user.setLastLoginTime(new Date(System.currentTimeMillis()));
		userServiceClient.updateUser(user);*/
		
		//获取权限
		Subject subject = SecurityUtils.getSubject();
		UserSessionVo vo = userServiceClient.getPermission(user);
		vo.setUser(user);
		subject.getSession().setAttribute(Constants.USER_LOGIN_SESSION, vo);
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, account,getName());
		return info;
		
		
	}

}
