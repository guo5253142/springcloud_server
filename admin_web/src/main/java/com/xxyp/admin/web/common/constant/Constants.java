package com.xxyp.admin.web.common.constant;



public class Constants {
	
	/*用户登录 SESSION 名*/
	public static final String USER_LOGIN_SESSION="usersession";
	
	//当前一级菜单
	public static final String CURR_FIRST_MENUID="currfirstmenuid";
	//当前二级菜单
	public static final String CURR_SECOND_MENUID="currsecondmenuid";
	
	//菜单数据
	public static final String USER_MENU_VO="usermenuvo";
	
	
	/** token **/
	public static final String TOKENID= "tokenid";
	
	//登录盐值
	public static final String LOGIN_SYSTEM_YZ="didi#3ddf332222%%%^@DDSSDssss";
	
	//默认密码
	public static final String DEFAULT_PASSWD="123456";
	//登录失败次数 锁定10分钟
	public static final int LOGIN_ERROR_COUNT=20;
	
	//验证码
	public static final String RANDOM_CODE="randomCode";
	
	//ajax返回状态 0失败 1成功 2跳转登录页面 
	public static final int AJAX_SUCCESS=1;
	public static final int AJAX_FAIL=0;
	public static final int AJAX_RE_LOGIN=2;
	public static final String RE_LOGIN_TIP="您已经在其他地方登录，请重新登录";
	public static final String NO_AUTH_TIP="您没有权限进行此操作！";
	
	
	//登录页面url
	public static final String index_url="/index";
	
	
	
}

