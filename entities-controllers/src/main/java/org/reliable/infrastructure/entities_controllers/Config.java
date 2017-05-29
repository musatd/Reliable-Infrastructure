package org.reliable.infrastructure.entities_controllers;

import org.reliable.infrastructure.entities_controllers.util.EntitiesService;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class Config {

	private static final String AMQP_SENDER_URL = "http://AMQP-SENDER";

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	
	@Bean
	public EntitiesService createAdminService() {
		return new EntitiesService(AMQP_SENDER_URL);
	}

}
