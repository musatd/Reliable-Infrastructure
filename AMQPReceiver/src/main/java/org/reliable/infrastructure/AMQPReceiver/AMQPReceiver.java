package org.reliable.infrastructure.AMQPReceiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class AMQPReceiver {
	
	
    public static void main( String[] args ) {
    	SpringApplication.run(AMQPReceiver.class, args);
    }
    
    @Bean
    public Receiver receiver() {
        return new Receiver();
    }
}
