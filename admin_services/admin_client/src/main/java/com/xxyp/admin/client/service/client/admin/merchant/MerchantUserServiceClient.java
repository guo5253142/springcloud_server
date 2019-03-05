package com.xxyp.admin.client.service.client.admin.merchant;

import org.springframework.cloud.openfeign.FeignClient;

import com.xxyp.admin.common.service.merchant.MerchantUserService;


@FeignClient(value = "admin-service", path="/merchantUser")
public interface MerchantUserServiceClient extends MerchantUserService{

}