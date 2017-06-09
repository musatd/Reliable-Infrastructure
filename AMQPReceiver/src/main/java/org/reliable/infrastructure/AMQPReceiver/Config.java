package org.reliable.infrastructure.AMQPReceiver;

import org.reliable.infrastructure.AMQPReceiver.messagesTasks.AMQPReceiverService;
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
	public AMQPReceiverService createAdminService() {
		return new AMQPReceiverService(ENTITIES_CONTROLLERS_URL);
	}
	
    @Bean
    public Receiver receiver() {
        return new Receiver();
    }
}
