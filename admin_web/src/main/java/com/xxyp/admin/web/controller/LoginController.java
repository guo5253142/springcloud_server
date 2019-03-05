package com.xxyp.admin.web.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xxyp.admin.client.service.client.admin.system.UserServiceClient;
import com.xxyp.admin.common.model.system.User;
import com.xxyp.admin.common.model.system.vo.AdminMenuVo;
import com.xxyp.admin.common.model.system.vo.UserSessionVo;
import com.xxyp.admin.common.util.MD5;
import com.xxyp.admin.common.util.RandomImageCode;
import com.xxyp.admin.common.util.StringUtil;
import com.xxyp.admin.web.common.constant.Constants;

@Controller
public class LoginController extends BaseController<Object>{
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private HttpServletRequest request;
	@Resource
	private UserServiceClient userServiceClient;
	
	
	
	@RequestMapping("/home")
	public ModelAndView home(ModelMap map) {
		ModelAndView mv = new ModelAndView("home");
		return mv;
	}
	
	/*@RequestMapping({"/", "/index"})
	public ModelAndView index(ModelMap map) {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}*/
	
	//sso登录后获取权限
	@RequestMapping({"/", "/index"})
	public ModelAndView ssoLogin(String account, ModelMap map,
			HttpServletResponse response,HttpServletRequest request) throws IOException {
		ModelAndView mv = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		//验证是否已经获取权限
		if (subject.isAuthenticated()) {
			//成功	
			mv.setViewName("redirect:/main");
			return mv;
		}
		
		if(StringUtil.isBlank(account)) {
			account=request.getAttribute("account").toString();
		}
		
		//创建用户名和密码的令牌
		UsernamePasswordToken token = new UsernamePasswordToken(account,account);
		try {
			subject.login(token);
		}catch (AuthenticationException e) {
			map.put("message", "您使用的账号因未知原因验证失败，请与您的供应商联系后重试！");
		}
		
		//验证是否获取权限成功
		if (subject.isAuthenticated()) {
			//成功	
			mv.setViewName("redirect:/main");
		}else{ 
			//失败
			mv.setViewName("noauthorize");
		}
		
		return mv;
	}
	
/*	@RequestMapping("/login")
	public ModelAndView login(String account, String password, String code, ModelMap map,
			HttpServletResponse response,HttpServletRequest request) throws IOException {
		ModelAndView mv = new ModelAndView("login");
		try {
			if (!loginCheck(account, password, code, map, response,request)) {
				return mv;
			}
		} catch (Throwable e) {
			logger.error(String.format("用户[%s]登录异常", account),e);
			map.put("message", "运行时异常,请联系管理员");
			return mv;
		}
		mv.setViewName("redirect:/main");
		return mv;
	}*/
	
	@RequestMapping("/main")
	public ModelAndView main() {
		Subject subject = SecurityUtils.getSubject();
		UserSessionVo vo=(UserSessionVo)subject.getSession().getAttribute(Constants.USER_LOGIN_SESSION);
		
		ModelAndView mv = new ModelAndView();
		Map<String, List<AdminMenuVo>> menuMap = null;
		if (vo == null) {
			mv.setViewName("redirect:/index");
			return mv;
		}
		
		menuMap = userServiceClient.getMenu(vo.getPermissionMap());
		mv.addObject("menuMap", menuMap);
		mv.setViewName("main");
		return mv;
	}
	
	/***
	 * 登陆验证
	 * 
	 */
	private boolean loginCheck(String account, String password, String code,
			ModelMap map, HttpServletResponse response,HttpServletRequest request) {
		if(StringUtil.isBlank(account)||StringUtil.isBlank(password)) {
			map.put("message", "用户名和密码不能为空");
			return false;
		}
		// 验证码
		if (StringUtil.isBlank(code)) {
			map.put("message", "请输入验证码");
			return false;
		}
		if (!code.equalsIgnoreCase((String) request.getSession().getAttribute(Constants.RANDOM_CODE))) {
			map.put("message", "验证码输入错误");
			return false;
		}
		Subject subject = SecurityUtils.getSubject();
		//创建用户名和密码的令牌
		UsernamePasswordToken token = new UsernamePasswordToken(account,password);
		//token.setRememberMe(true);
		try {
			subject.login(token);
		} catch (UnknownAccountException ex) {	
			map.put("message", "登陆失败！用户不存在");
			return false;
		} catch (IncorrectCredentialsException ex) {
			map.put("message", "密码错误!");
			return false;
		} catch (DisabledAccountException ex) {
			map.put("message", "用户已被禁用!");
			return false;
		} catch (AuthenticationException e) {
			map.put("message", "您使用的账号因未知原因验证失败，请与您的供应商联系后重试！");
			return false;
		}
		
		//验证是否成功登录的方法
		if (subject.isAuthenticated()) {	//登录成功	
			return true;
		}else{ //登录失败
			return false;
		}
		
	}
	
	/**
	  *    退出登录
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletResponse response,HttpServletRequest request) throws IOException {
		ModelAndView mv = new ModelAndView();
		if(null!=request.getSession().getAttribute(Constants.USER_LOGIN_SESSION)) {
			logger.info("用户：{}，退出登录",getUser(request).getUser().getAccount());
			request.getSession().removeAttribute(Constants.USER_LOGIN_SESSION);
			request.getSession().invalidate();
		}
		
		mv.setViewName("redirect:/index");
		return mv;
	}
	

	/**
     * 当前用户密码修改UI
     */
	@RequestMapping("/showPasswordModify")
    public ModelAndView passwordmodifyui(ModelMap modelMap, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/system/passwordModify");
        return mv;
    }
	
	@RequestMapping("/rcajaxPasswordModify")
    public void passwordModify(HttpServletResponse response,String oldPassword, String newPassword, String rePassword) {
		UserSessionVo vo=(UserSessionVo)request.getSession().getAttribute(Constants.USER_LOGIN_SESSION);
		User user = userServiceClient.getUserByAccount(vo.getUser().getAccount());
        if(!newPassword.equals(rePassword)){
        	outJson(response,this.AJAX_FAIL, "新密码两次输入不一致");
        	return;
        }else if(oldPassword.length() < 5 || oldPassword.length() > 10 || newPassword.length() < 5 || newPassword.length() > 10){
        	outJson(response,this.AJAX_FAIL, "密码长度必须在5~10字符之间！");
        	return;
        }else if(!user.getPassword().equals(MD5.encode1(oldPassword))) {
            outJson(response,this.AJAX_FAIL, "输入的旧密码与原来密码不一致！");
        	return;
        }else {
            user.setPassword(MD5.encode1(newPassword));
            userServiceClient.updateUser(user);
            outJson(response,this.AJAX_SUCCESS, "修改密码成功！");
        }

    }
	
	@RequestMapping("/noauthorize")
    public ModelAndView noauthorize(ModelMap modelMap, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("noauthorize");
        return mv;
    }
	
	/**
	 * 获取验证码
	 * @param response
	 */
	@RequestMapping("/randomImageCode")
	public void randomImageCode(HttpServletResponse response) {
		try {
			// 设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			HttpSession session = request.getSession();
			
			// 在内存中创建图象
			RandomImageCode randImageCode=new RandomImageCode();
			BufferedImage image = randImageCode.getImage();

			// 将认证码存入SESSION
			session.setAttribute(Constants.RANDOM_CODE, randImageCode.getsRand());

			logger.info("验证码：" + randImageCode.getsRand());
			// 输出图象到页面
			ImageIO.write(image, "JPEG", response.getOutputStream());
			image = null;
		} catch (Exception e) {
			logger.error("获取验证码，发生异常：", e);
		}
	}
	
	
}
