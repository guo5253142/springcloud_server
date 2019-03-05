package com.xxyp.admin.client.service.client.admin.system;

import org.springframework.cloud.openfeign.FeignClient;

import com.xxyp.admin.common.service.system.ModuleService;


@FeignClient(value = "admin-service" ,path="/module")
public interface ModuleServiceClient  extends ModuleService{

}