package com.xxyp.admin.client.service.client.admin.merchant;

import org.springframework.cloud.openfeign.FeignClient;

import com.xxyp.admin.common.service.merchant.MerchantPermissionService;


@FeignClient(value = "admin-service" ,path="/merchantPermission")
public interface MerchantPermissionServiceClient  extends MerchantPermissionService{

}