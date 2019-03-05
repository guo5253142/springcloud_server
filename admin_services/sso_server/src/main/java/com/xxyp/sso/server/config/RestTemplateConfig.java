package com.xxyp.sso.server.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
//@Configuration
public class RestTemplateConfig {
	
	/*@Value("${root.merchant.host}")
	String rootUri ;
	@Bean("merchantRestTemplate")
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		   return builder.rootUri(rootUri).build();
		}*/
}

