package com.xxyp.admin.client.service.client.admin.merchant;

import org.springframework.cloud.openfeign.FeignClient;

import com.xxyp.admin.common.service.merchant.MerchantRolePermissionService;


@FeignClient(value = "admin-service", path="/merchantRolePermission")
public interface MerchantRolePermissionServiceClient extends MerchantRolePermissionService{

}