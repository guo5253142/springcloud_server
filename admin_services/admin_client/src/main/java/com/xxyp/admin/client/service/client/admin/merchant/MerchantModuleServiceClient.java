package com.xxyp.admin.client.service.client.admin.merchant;

import org.springframework.cloud.openfeign.FeignClient;

import com.xxyp.admin.common.service.merchant.MerchantModuleService;


@FeignClient(value = "admin-service" ,path="/merchantModule")
public interface MerchantModuleServiceClient  extends MerchantModuleService{

}