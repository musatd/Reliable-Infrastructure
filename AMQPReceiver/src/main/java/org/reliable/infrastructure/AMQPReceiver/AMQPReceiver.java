package org.reliable.infrastructure.AMQPReceiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;



@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(Config.class)
public class AMQPReceiver {
	
    public static void main( String[] args ) {
    	SpringApplication.run(AMQPReceiver.class, args);
    }
}
