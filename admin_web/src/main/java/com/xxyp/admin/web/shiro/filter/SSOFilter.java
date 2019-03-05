package com.xxyp.admin.web.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * SSO 单点登录过滤器
 * @author guopeng1
 * 2019年1月23日13:41:33
 */
public class SSOFilter extends PathMatchingFilter{
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${sso.serviceUrl}")
	private String serviceUrl;
	
	@Value("${sso.cookieName}")
	private String cookieName;
	
	
	@Override
    protected boolean onPreHandle(ServletRequest req, ServletResponse resp, Object mappedValue) throws Exception {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
        String path = request.getContextPath();
		String gotoURL = request.getParameter("gotoURL");
		if(gotoURL == null) {
			gotoURL = request.getRequestURL().toString();
		}
		String URL = serviceUrl + "?action=preLogin&setCookieURL=" + request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/setCookie&gotoURL=" + gotoURL;
		
		
		Cookie ticket = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals(cookieName)) {
					ticket = cookie;
					break;
				}
			}
		}
		String uri=request.getRequestURI();
		if(uri.indexOf(";")>0) {
			uri=uri.substring(0,uri.indexOf(";"));
		}
		if(uri.indexOf("?")>0) {
			uri=uri.substring(0,uri.indexOf("?"));
		}
		if(uri.equals(path + "/logout")) {
			doLogout(request, response, ticket, URL);
			return true;
		}else if(uri.equals(path + "/setCookie")) {
			setCookie(request, response);
			return false;
		}else if (ticket != null) {
			//验证ticket
			if(authCookie(request, response, ticket)) {
				return true;
			}
		} 
		response.sendRedirect(URL);
        
        return false;
 
    }
	/**
	 * 设置cookie 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void setCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Cookie ticket = new Cookie(cookieName, request.getParameter("ticket"));
		ticket.setPath("/");
		ticket.setMaxAge(Integer.parseInt(request.getParameter("expiry")));
		response.addCookie(ticket);
		
		String gotoURL = request.getParameter("gotoURL");
		if(gotoURL != null) {
			response.sendRedirect(gotoURL);
		}
	}
	
	/**
	 * 验证tick
	 * @param request
	 * @param response
	 * @param ticket
	 * @return
	 * @throws Exception
	 */
	private boolean authCookie(HttpServletRequest request, HttpServletResponse response, Cookie ticket) throws Exception{
		NameValuePair[] params = new NameValuePair[2];
		params[0] = new NameValuePair("action", "authTicket");
		params[1] = new NameValuePair("cookieName", ticket.getValue());
		try {
			JSONObject result = post(request, response, params);
			if(result ==null || result.getBoolean("error")) {
				return false;
			} 
			request.setAttribute("account", result.getString("username"));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	   *  退出登录
	 * @param request
	 * @param response
	 * @param chain
	 * @param ticket
	 * @param URL
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doLogout(HttpServletRequest request, HttpServletResponse response, Cookie ticket, String URL) throws IOException, ServletException {
		NameValuePair[] params = new NameValuePair[2];
		params[0] = new NameValuePair("action", "logout");
		params[1] = new NameValuePair("cookieName", ticket.getValue());
		try {
			post(request, response, params);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		} /*finally {
			response.sendRedirect(URL);
		}*/
	}
	
	private JSONObject post(HttpServletRequest request, HttpServletResponse response, NameValuePair[] params) throws IOException, ServletException, JSONException {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(serviceUrl);
		postMethod.addParameters(params);
		switch(httpClient.executeMethod(postMethod)) {
			case HttpStatus.SC_OK: //
				
				return JSONObject.parseObject(postMethod.getResponseBodyAsString());
			default:
				// 其它处理
				return null;
		}
	}


}
