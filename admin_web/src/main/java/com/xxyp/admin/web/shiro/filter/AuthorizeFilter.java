package com.xxyp.admin.web.shiro.filter;

import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxyp.admin.common.model.system.Permission;
import com.xxyp.admin.common.model.system.PermissionItem;
import com.xxyp.admin.common.model.system.vo.UserSessionVo;
import com.xxyp.admin.common.util.StringUtil;
import com.xxyp.admin.web.common.constant.Constants;
import com.xxyp.admin.web.common.model.AjaxResultDto;

/**
    * 权限访问控制
 * @author guopeng1
 * 2019年1月2日17:45:38
 */
public class AuthorizeFilter extends PathMatchingFilter{
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
 
        //请求的url
        String requestURL = getPathWithinApplication(request);
        Subject subject = SecurityUtils.getSubject();
        Object o=subject.getSession().getAttribute(Constants.USER_LOGIN_SESSION);
        
        HttpServletRequest req = (HttpServletRequest) request;
        String requestedWith = req.getHeader("x-requested-with");
    	boolean isajax = (requestedWith != null && "XMLHttpRequest".equals(requestedWith));
    	
        if (!subject.isAuthenticated()||null==o){
        	 // 如果没有登录, 直接返回 进入登录流程
        	if (isajax) {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				
				AjaxResultDto dto=new AjaxResultDto();
				dto.setMsg(Constants.RE_LOGIN_TIP);
				dto.setStatus(Constants.AJAX_RE_LOGIN);
				dto.setUrl(Constants.index_url);
				response.getWriter().write(dto.getJsonString());
			}else {
				WebUtils.issueRedirect(request, response, Constants.index_url);
			}
           
            return  false;
        }
        String method=requestURL.substring(requestURL.lastIndexOf("/")+1, requestURL.length());
        if(method.startsWith("rcajax")) {
        	 return  true;
        }
        UserSessionVo vo=(UserSessionVo)o;
 
        boolean hasPermission = false;
        for(Entry<String, Permission> entry :vo.getPermissionMap().entrySet()){
			if(!StringUtil.isBlank(entry.getValue().getMenuUrl())) {
				String url=entry.getValue().getMenuUrl();
				if(url.equals(requestURL)) {
					hasPermission=true;
				}
			}
			
		}
        for(Entry<String, PermissionItem> entry :vo.getItemMap().entrySet()){
			PermissionItem item=entry.getValue();
			if(!StringUtil.isBlank(item.getMethodValue1())) {
				String url=item.getMethodValue1();
				if(url.equals(requestURL)) {
					hasPermission=true;
				}
			}
			if(!StringUtil.isBlank(item.getMethodValue2())) {
				String url=item.getMethodValue2();
				if(url.equals(requestURL)) {
					hasPermission=true;
				}
			}
		}
        
        if (hasPermission){
            return true;
        }else {
        	logger.info("当前用户没有访问路径" + requestURL + "的权限");
        	// 如果是ajax
			if (isajax) {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				AjaxResultDto dto=new AjaxResultDto();
				dto.setMsg(Constants.NO_AUTH_TIP);
				dto.setStatus(Constants.AJAX_FAIL);
				response.getWriter().write(dto.getJsonString());
			} else {
				// 普通页面请求
				WebUtils.issueRedirect(request, response, "/noauthorize");
			}
            return false;
 
        }
 
    }


}
