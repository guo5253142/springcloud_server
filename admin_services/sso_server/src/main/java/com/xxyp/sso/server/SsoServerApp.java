package com.xxyp.sso.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.xxyp.sso.server")
@SpringBootApplication
public class SsoServerApp {
    public static void main( String[] args ){
    	SpringApplication.run(SsoServerApp.class, args);
    }
}
