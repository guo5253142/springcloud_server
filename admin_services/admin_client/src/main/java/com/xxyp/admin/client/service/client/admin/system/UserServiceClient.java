package com.xxyp.admin.client.service.client.admin.system;

import org.springframework.cloud.openfeign.FeignClient;

import com.xxyp.admin.common.service.system.UserService;


@FeignClient(value = "admin-service" ,path="/user")
public interface UserServiceClient  extends UserService{

}