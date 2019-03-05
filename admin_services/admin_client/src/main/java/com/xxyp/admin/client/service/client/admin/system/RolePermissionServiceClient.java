package com.xxyp.admin.client.service.client.admin.system;

import org.springframework.cloud.openfeign.FeignClient;

import com.xxyp.admin.common.service.system.RolePermissionService;


@FeignClient(value = "admin-service", path="/rolePermission")
public interface RolePermissionServiceClient extends RolePermissionService{

}