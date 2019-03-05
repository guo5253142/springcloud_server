package com.xxyp.admin.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;

@Configuration
public class RibbonRule {
	@Bean
    public IRule rule() {
        return new BestAvailableRule();
    }
}
