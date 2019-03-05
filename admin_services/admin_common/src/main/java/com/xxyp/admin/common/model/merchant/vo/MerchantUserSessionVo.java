package com.xxyp.admin.common.model.merchant.vo;

import java.io.Serializable;
import java.util.Map;

import com.xxyp.admin.common.model.merchant.MerchantPermission;
import com.xxyp.admin.common.model.merchant.MerchantPermissionItem;
import com.xxyp.admin.common.model.merchant.MerchantUser;


/**
 * 登录用户VO，包括登录用户权限值，登录后用户信息
 * @author guopeng
 *
 */
@SuppressWarnings("serial")
public class MerchantUserSessionVo implements Serializable {
    /* 上线登录时间 */
    private String lastLoginTime;
    
    /* 用户信息 */
    private MerchantUser user;

    /* 一级权限列表 */
    private Map<String, MerchantPermission> permissionMap;
    
    /* 二级权限列表 */
    private Map<String, MerchantPermissionItem> itemMap;

 
    public Map<String, MerchantPermission> getPermissionMap() {
        return permissionMap;
    }

    public void setPermissionMap(Map<String, MerchantPermission> permissionMap) {
        this.permissionMap = permissionMap;
    }

    public Map<String, MerchantPermissionItem> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<String, MerchantPermissionItem> itemMap) {
        this.itemMap = itemMap;
    }

    public MerchantUser getUser() {
        return user;
    }

    public void setUser(MerchantUser user) {
        this.user = user;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
