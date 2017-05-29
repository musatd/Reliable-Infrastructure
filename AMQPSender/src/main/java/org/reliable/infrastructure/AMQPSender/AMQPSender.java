package org.reliable.infrastructure.AMQPSender;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
public class AMQPSender {
	private static final Integer MAX_NR_PRIORITIES = 4;
	private static final String QUEUE_NAME = "Reliable-Test";
	
	
    public static void main(String args[]) {
    	SpringApplication.run(AMQPSender.class, args);
    }
    
    
    @Bean
    public Queue hello() {
    	Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-max-priority", MAX_NR_PRIORITIES);
        return new Queue(QUEUE_NAME, true, false, false, args);
    }

}
