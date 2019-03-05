package com.xxyp.admin.client.service.client.admin.system;

import org.springframework.cloud.openfeign.FeignClient;

import com.xxyp.admin.common.service.system.PermissionService;


@FeignClient(value = "admin-service" ,path="/permission")
public interface PermissionServiceClient  extends PermissionService{

}