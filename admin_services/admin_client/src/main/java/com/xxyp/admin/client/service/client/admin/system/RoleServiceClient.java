package com.xxyp.admin.client.service.client.admin.system;

import org.springframework.cloud.openfeign.FeignClient;

import com.xxyp.admin.common.service.system.RoleService;


@FeignClient(value = "admin-service", path="/role")
public interface RoleServiceClient extends RoleService{

}