package com.xxyp.admin.client.service.client.admin.system;

import org.springframework.cloud.openfeign.FeignClient;

import com.xxyp.admin.common.service.system.PermissionItemService;


@FeignClient(value = "admin-service" ,path="/permissionItem")
public interface PermissionItemServiceClient  extends PermissionItemService{

}