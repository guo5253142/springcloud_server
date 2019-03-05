package com.xxyp.sso.server.controller.system;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xxyp.admin.common.common.constant.YesOrNoType;
import com.xxyp.admin.common.model.system.User;
import com.xxyp.admin.common.service.system.UserService;
import com.xxyp.admin.common.util.MD5;
import com.xxyp.admin.common.util.RandomImageCode;
import com.xxyp.admin.common.util.StringUtil;
import com.xxyp.sso.server.common.constant.Constants;
import com.xxyp.sso.server.common.model.Ticket;
import com.xxyp.sso.server.common.util.DESUtils;
import com.xxyp.sso.server.schedule.RecoverTicket;

@Controller
public class SSOController{
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private HttpServletRequest request;
	
	@Resource
	private UserService userService;
	
	/** cookie名称 */
	@Value("${ssoauthic.cookieName}")
	private String cookieName;
	
	/** 是否安全协议 */
	@Value("${ssoauthic.secure}")
	private boolean secure;
	
	/** 密钥 */
	@Value("${ssoauthic.secretKey}")
	private String secretKey;
	
	/** ticket有效时间 */
	@Value("${ssoauthic.ticketTimeout}")
	private  int ticketTimeout;
	
	/** 单点登录标记 */
	private static Map<String, Ticket> tickets;
	
	/** 回收ticket线程池 */
	private static ScheduledExecutorService schedulePool;
	
	static {
		tickets = new ConcurrentHashMap<String, Ticket>();
		schedulePool = Executors.newScheduledThreadPool(1);
		schedulePool.scheduleAtFixedRate(new RecoverTicket(tickets), 0, 1, TimeUnit.MINUTES);
	}
	
	/**
	 * remark 登录页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/preLogin")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response,ModelMap map,
			String setCookieURL,String gotoURL,String message) {
		ModelAndView mv = new ModelAndView("login");
		map.put("message", message);
		map.put("setCookieURL", setCookieURL);
		map.put("gotoURL", gotoURL);
		return mv;
	}
	
	@RequestMapping("/login")
	private void doLogin(String account, String password, String code,String setCookieURL,String gotoURL,
			HttpServletRequest request, HttpServletResponse response,ModelMap map) throws Exception{
		User user = userService.getUserByAccount(account);
		String url="/preLogin?setCookieURL="+setCookieURL+"&gotoURL="+gotoURL;
		if(StringUtil.isBlank(account)||StringUtil.isBlank(password)) {
			map.put("message", "用户名和密码不能为空");
			request.getRequestDispatcher(url+"&message=用户名和密码不能为空").forward(request, response);
			return;
		}
		// 验证码
		if (StringUtil.isBlank(code)) {
			request.getRequestDispatcher(url+"&message=请输入验证码").forward(request, response);
			return;
		}
		if (!code.equalsIgnoreCase((String) request.getSession().getAttribute(Constants.RANDOM_CODE))) {
			request.getRequestDispatcher(url+"&message=验证码输入错误").forward(request, response);
			return ;
		}
		//用户不存在
		if (user==null){
			request.getRequestDispatcher(url+"&message=登陆失败！用户不存在").forward(request, response);
			return;
		}
		if (user.getStatus()==YesOrNoType.no.getIndex()) {
			//账号禁用 ，访问受限
			request.getRequestDispatcher(url+"&message=用户已被禁用").forward(request, response);
			return;
		}
		//验正密码
		if (!user.getPassword().equals(MD5.encode1(password))) {
			request.getRequestDispatcher(url+"&message=密码错误").forward(request, response);
			return;
		}else {
			logger.info("登录成功："+account);
			user.setLastLoginTime(new Date(System.currentTimeMillis()));
			userService.updateUser(user);
			
			String ticketKey = UUID.randomUUID().toString().replace("-", "");
			String encodedticketKey = DESUtils.encrypt(ticketKey, secretKey);
			
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			Calendar cal = Calendar.getInstance();
			cal.setTime(createTime);
			cal.add(Calendar.MINUTE, ticketTimeout);
			Timestamp recoverTime = new Timestamp(cal.getTimeInMillis());
			Ticket ticket = new Ticket(account, createTime, recoverTime);
			
			tickets.put(ticketKey, ticket);

			String[] checks = request.getParameterValues("autoAuth");
			int expiry = -1;
			if(checks != null && "1".equals(checks[0]))
				expiry = 7 * 24 * 3600;
			Cookie cookie = new Cookie(cookieName, encodedticketKey);
			cookie.setSecure(secure);// 为true时用于https
			cookie.setMaxAge(expiry);
			cookie.setPath("/");
			response.addCookie(cookie);
			
			gotoURL+="?account="+account;
			
			PrintWriter out = response.getWriter();
			out.print("<script type='text/javascript'>");
			out.print("document.write(\"<form id='url' method='post' action='" + setCookieURL + "'>\");");
			out.print("document.write(\"<input type='hidden' name='gotoURL' value='" + gotoURL + "' />\");");
			out.print("document.write(\"<input type='hidden' name='ticket' value='" + encodedticketKey + "' />\");");
			out.print("document.write(\"<input type='hidden' name='expiry' value='" + expiry + "' />\");");
			out.print("document.write('</form>');");
			out.print("document.getElementById('url').submit();");
			out.print("</script>");
		}
		
	}
	
	
	@RequestMapping("/ssoAuthic")
	public void ssoAuthic(HttpServletRequest request, HttpServletResponse response,String action) {
		try {
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("text/html;charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			if("preLogin".equals(action)) {
				preLogin(request, response);
			}else if("logout".equals(action)) {
				doLogout(request, response);
			} else if("authTicket".equals(action)) {
				authTicket(request, response);
			} else {
				System.err.println("指令错误");
				out.print("Action can not be empty！");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void preLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie ticket = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null)
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals(cookieName)) {
					ticket = cookie;
					break;
				}
			}
		if(ticket == null) {
			request.getRequestDispatcher("/preLogin").forward(request, response);
		} else {
			String encodedTicket = ticket.getValue();
			String decodedTicket = DESUtils.decrypt(encodedTicket, secretKey);
			if(tickets.containsKey(decodedTicket)) {
				String setCookieURL = request.getParameter("setCookieURL");
				String gotoURL = request.getParameter("gotoURL");
				if(setCookieURL != null)
	                response.sendRedirect(setCookieURL + "?ticket=" + encodedTicket + "&expiry=" + ticket.getMaxAge() + "&gotoURL=" + gotoURL);
			} else {
				request.getRequestDispatcher("preLogin").forward(request, response);
			}
		}
	}
	
	private void authTicket(HttpServletRequest request, HttpServletResponse response) throws IOException {
		StringBuilder result = new StringBuilder("{");
		PrintWriter out = response.getWriter();
		String encodedTicket = request.getParameter("cookieName");
		if(encodedTicket == null) {
			result.append("\"error\":true,\"errorInfo\":\"Ticket can not be empty!\"");
		} else {
			String decodedTicket = DESUtils.decrypt(encodedTicket, secretKey);
			if(tickets.containsKey(decodedTicket))
				result.append("\"error\":false,\"username\":\"").append(tickets.get(decodedTicket).getUsername()+"\"");
			else
				result.append("\"error\":true,\"errorInfo\":\"Ticket is not found!\"");
		}
		result.append("}");
		out.print(result);
	}

	private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		StringBuilder result = new StringBuilder("{");
		PrintWriter out = response.getWriter();
		String encodedTicket = request.getParameter("cookieName");
		if(encodedTicket == null) {
			result.append("\"error\":true,\"errorInfo\":\"Ticket can not be empty!\"");
		} else {
			String decodedTicket = DESUtils.decrypt(encodedTicket, secretKey);
			tickets.remove(decodedTicket);
			result.append("\"error\":false");
		}
		result.append("}");
		out.print(result);
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
