package org.reliable.infrastructure.entities_controllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(Config.class)
public class EntitiesControllers {
	
    public static void main( String[] args ) {
    	SpringApplication.run(EntitiesControllers.class, args);
    }
}
