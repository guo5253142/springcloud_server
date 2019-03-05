package com.xxyp.admin.web.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.xxyp.admin.web.shiro.filter.AuthorizeFilter;
import com.xxyp.admin.web.shiro.filter.KickoutSessionControlFilter;
import com.xxyp.admin.web.shiro.filter.SSOFilter;



@Configuration
public class ShiroConfiguration {
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${admin.maxSessionNum:1}")
	private int maxSessionNum;
	
	@Value("${spring.redis.host}")
	private String redisHost;
	
	@Value("${spring.redis.port}")
	private Integer redisPort;
	
	/**
     * Shiro生命周期处理器
     *
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
	
	 /***
	 * s 授权所用配置
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /***
            * 使授权注解起作用不如不想配置可以在pom文件中加入
     * <dependency>
     *<groupId>org.springframework.boot</groupId>
     *<artifactId>spring-boot-starter-aop</artifactId>
     *</dependency>
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	   return new PropertySourcesPlaceholderConfigurer();
	}

    @Bean
   	public SSOFilter getSSOFilter() {
   	   return new SSOFilter();
   	}
    
	@Bean
	    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
	        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
	        shiroFilterFactoryBean.setSecurityManager(securityManager);
	        // 没有登陆的用户只能访问登陆页面
	        shiroFilterFactoryBean.setLoginUrl("/index");
	        // 登录成功后要跳转的链接
	        //shiroFilterFactoryBean.setSuccessUrl("/main");
	        // 未授权界面; ----这个配置了没卵用，具体原因想深入了解的可以自行百度
	        //shiroFilterFactoryBean.setUnauthorizedUrl("/auth/403");
	        //自定义拦截器
	        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
	        //限制同一帐号同时在线的个数。
	        filtersMap.put("kickout", kickoutSessionControlFilter());
	        //访问权限配置
	        filtersMap.put("requestUrl", new AuthorizeFilter());
	        //SSO单点登录控制
	        filtersMap.put("ssoAuthic", getSSOFilter());
	        
	        shiroFilterFactoryBean.setFilters(filtersMap);
	        // 权限控制map.
	        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
	        filterChainDefinitionMap.put("/static/**", "anon");
	        filterChainDefinitionMap.put("/css/**", "anon");
	        filterChainDefinitionMap.put("/js/**", "anon");
	        filterChainDefinitionMap.put("/img/**", "anon");
	        filterChainDefinitionMap.put("/home", "anon");
	        filterChainDefinitionMap.put("/login", "anon");
	       
	        
	        filterChainDefinitionMap.put("/main", "anon");
	       
	        filterChainDefinitionMap.put("/noauthorize", "anon");
	        filterChainDefinitionMap.put("/randomImageCode", "anon");
	        filterChainDefinitionMap.put("/showPasswordModify", "anon");
	        filterChainDefinitionMap.put("/index", "ssoAuthic");
	        filterChainDefinitionMap.put("/setCookie", "ssoAuthic");
	        filterChainDefinitionMap.put("/logout", "ssoAuthic");
	        
	        //filterChainDefinitionMap.put("/**", "authc,kickout");
	        filterChainDefinitionMap.put("/**", "requestUrl,kickout");
	        
	        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
	        logger.info("-------------Shiro拦截器工厂类注入成功-------------");
	        return shiroFilterFactoryBean;
	    }

	    @Bean
	    public SecurityManager securityManager() {
	        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
	        // 自定义缓存实现 使用redis
	        securityManager.setCacheManager(cacheManager());
	        // 自定义session管理 使用redis
	        securityManager.setSessionManager(sessionManager());
	        // 设置realm.
	        securityManager.setRealm(myShiroRealm());
	        return securityManager;
	    }

	    /**
	     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
	     *
	     * @return
	     */
	    @Bean
	    public AdminShiroRealm myShiroRealm() {
	    	AdminShiroRealm myShiroRealm = new AdminShiroRealm();
	        return myShiroRealm;
	    }

	    /**
	     * cacheManager 缓存 redis实现
	     * 使用的是shiro-redis开源插件
	     *
	     * @return
	     */
	    public RedisCacheManager cacheManager() {
	        RedisCacheManager redisCacheManager = new RedisCacheManager();
	        redisCacheManager.setRedisManager(redisManager());
	        return redisCacheManager;
	    }

	    /**
	     * 配置shiro redisManager
	     * 使用的是shiro-redis开源插件
	     *
	     * @return
	     */
	    public RedisManager redisManager() {
	        RedisManager redisManager = new RedisManager();
	        redisManager.setHost(redisHost);
	        redisManager.setPort(redisPort);
	        redisManager.setExpire(1800);// 配置缓存过期时间
	        redisManager.setTimeout(0);
	        // redisManager.setPassword(password);
	        return redisManager;
	    }

	    /**
	     * Session Manager
	     * 使用的是shiro-redis开源插件
	     */
	    @Bean
	    public DefaultWebSessionManager sessionManager() {
	        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
	        sessionManager.setSessionDAO(redisSessionDAO());
	        return sessionManager;
	    }

	    /**
	     * RedisSessionDAO shiro sessionDao层的实现 通过redis
	     * 使用的是shiro-redis开源插件
	     */
	    @Bean
	    public RedisSessionDAO redisSessionDAO() {
	        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
	        redisSessionDAO.setRedisManager(redisManager());
	        return redisSessionDAO;
	    }

	    /**
	     * 限制同一账号登录同时登录人数控制
	     *
	     * @return
	     */
	    @Bean
	    public KickoutSessionControlFilter kickoutSessionControlFilter() {
	        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
	        kickoutSessionControlFilter.setCacheManager(cacheManager());
	        kickoutSessionControlFilter.setSessionManager(sessionManager());
	        kickoutSessionControlFilter.setKickoutAfter(false);
	        kickoutSessionControlFilter.setMaxSession(maxSessionNum);
	        kickoutSessionControlFilter.setKickoutUrl("/logout");
	        return kickoutSessionControlFilter;
	    }
	    
	    /**
	                *   访问 权限 拦截器
	     * @return
	     */
	   /* @Bean
	    public URLPathMatchingFilter getURLPathMatchingFilter() {
	        return new URLPathMatchingFilter();
	    }*/


	  
}

