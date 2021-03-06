package com.xxyp.admin.web.shiro.filter;

import java.io.Serializable;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.xxyp.admin.common.model.system.User;
import com.xxyp.admin.web.common.constant.Constants;
import com.xxyp.admin.web.common.model.AjaxResultDto;

/**
   *   同一个帐号最大会话数限制
 * @author guopeng1
 *  2019年1月2日17:44:49
 */
public class KickoutSessionControlFilter extends AccessControlFilter{
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());

	 private String kickoutUrl; //踢出后到的地址
	    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
	    private int maxSession = 1; //同一个帐号最大会话数 默认1

	    private SessionManager sessionManager;
	    private Cache<String, Deque<Serializable>> cache;

	    public void setKickoutUrl(String kickoutUrl) {
	        this.kickoutUrl = kickoutUrl;
	    }

	    public void setKickoutAfter(boolean kickoutAfter) {
	        this.kickoutAfter = kickoutAfter;
	    }

	    public void setMaxSession(int maxSession) {
	        this.maxSession = maxSession;
	    }

	    public void setSessionManager(SessionManager sessionManager) {
	        this.sessionManager = sessionManager;
	    }
	    //设置Cache的key的前缀
	    public void setCacheManager(CacheManager cacheManager) {
	        this.cache = cacheManager.getCache("shiro_admin_cache");
	    }

	    @Override
	    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
	        return false;
	    }

	    @Override
	    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
	        Subject subject = getSubject(request, response);
	        if(!subject.isAuthenticated() && !subject.isRemembered()) {
	            //如果没有登录，直接进行之后的流程
	            return true;
	        }


	        Session session = subject.getSession();
	        User user = (User) subject.getPrincipal();
	        String username = user.getAccount();
	        Serializable sessionId = session.getId();

	        //读取缓存   没有就存入
	        Deque<Serializable> deque = cache.get(username);

	        //如果此用户没有session队列，也就是还没有登录过，缓存中没有
	        //就new一个空队列，不然deque对象为空，会报空指针
	        if(deque==null){
	            deque = new LinkedList<Serializable>();
	        }

	        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
	        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
	            //将sessionId存入队列
	            deque.push(sessionId);
	            //将用户的sessionId队列缓存
	            cache.put(username, deque);
	        }

	        //如果队列里的sessionId数超出最大会话数，开始踢人
	        while(deque.size() > maxSession) {
	            Serializable kickoutSessionId = null;
	            if(kickoutAfter) { //如果踢出后者
	                kickoutSessionId = deque.removeFirst();
	                //踢出后再更新下缓存队列
	                cache.put(username, deque);
	            } else { //否则踢出前者
	                kickoutSessionId = deque.removeLast();
	                //踢出后再更新下缓存队列
	                cache.put(username, deque);
	            }



	            try {
	                //获取被踢出的sessionId的session对象
	                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
	                if(kickoutSession != null) {
	                    //设置会话的kickout属性表示踢出了
	                    kickoutSession.setAttribute("kickout", true);
	                }
	            } catch (Exception e) {//ignore exception
	            }
	        }

	        //如果被踢出了，直接退出，重定向到踢出后的地址
	        if (session.getAttribute("kickout") != null) {
	            //会话被踢出了
	            try {
	            	logger.info("用户：{}，被T出登录",username);
	                //退出登录
	                subject.logout();
	            } catch (Exception e) { //ignore
	            }
	            saveRequest(request);

	            Map<String, String> resultMap = new HashMap<String, String>();
	            //判断是不是Ajax请求
	            if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
	                response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					AjaxResultDto dto=new AjaxResultDto();
					dto.setMsg(Constants.RE_LOGIN_TIP);
					dto.setStatus(Constants.AJAX_RE_LOGIN);
					dto.setUrl(kickoutUrl);
					response.getWriter().write(dto.getJsonString());
	            }else{
	                //重定向
	                WebUtils.issueRedirect(request, response, kickoutUrl);
	            }
	            return false;
	        }
	        return true;
	    }
	    

}
