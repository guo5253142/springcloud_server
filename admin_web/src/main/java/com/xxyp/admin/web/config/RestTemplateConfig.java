package com.xxyp.admin.web.config;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@Configuration
public class RestTemplateConfig {
	
/*	@Value("${root.merchant.host}")
	String rootUri ;
	@Bean("merchantRestTemplate")
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		   return builder.rootUri(rootUri).build();
		}
	*/

	    @Bean
	    @LoadBalanced
	    public RestTemplate getRestTemplate() {
	        return new RestTemplate();
	    }
	
}

