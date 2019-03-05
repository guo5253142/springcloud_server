package com.xxyp.admin.client.service.client.admin.merchant;

import org.springframework.cloud.openfeign.FeignClient;

import com.xxyp.admin.common.service.merchant.MerchantInfoService;


@FeignClient(value = "admin-service", path="/merchantInfo")
public interface MerchantInfoServiceClient extends MerchantInfoService{

}