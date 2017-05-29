package org.reliable.infrastructure.admin_web_content;

import org.reliable.infrastructure.admin_web_content.controllers.util.AdminService;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
	
	private static final String ENTITIES_CONTROLLERS_URL = "http://ENTITIES-CONTROLLERS";

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	
	@Bean
	public AdminService createAdminService() {
		return new AdminService(ENTITIES_CONTROLLERS_URL);
	}

}
