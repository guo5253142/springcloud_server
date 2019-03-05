package com.xxyp.admin.client.service.client.admin.merchant;

import org.springframework.cloud.openfeign.FeignClient;

import com.xxyp.admin.common.service.merchant.MerchantPermissionItemService;


@FeignClient(value = "admin-service" ,path="/merchantPermissionItem")
public interface MerchantPermissionItemServiceClient  extends MerchantPermissionItemService{

}