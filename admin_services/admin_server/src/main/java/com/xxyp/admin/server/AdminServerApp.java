package com.xxyp.admin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.xxyp.admin.server")
@SpringBootApplication
public class AdminServerApp {
    public static void main( String[] args ){
    	SpringApplication.run(AdminServerApp.class, args);
    }
}
