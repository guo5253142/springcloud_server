package com.xxyp.admin.client.service.client.admin.merchant;

import org.springframework.cloud.openfeign.FeignClient;

import com.xxyp.admin.common.service.merchant.MerchantRoleService;


@FeignClient(value = "admin-service", path="/merchantRole")
public interface MerchantRoleServiceClient extends MerchantRoleService{

}