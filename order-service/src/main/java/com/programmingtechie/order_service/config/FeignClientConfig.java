package com.programmingtechie.order_service.config;

import feign.Logger;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    @LoadBalanced
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
