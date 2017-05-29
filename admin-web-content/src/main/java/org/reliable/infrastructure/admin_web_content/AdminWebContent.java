package org.reliable.infrastructure.admin_web_content;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;


//@ComponentScan(useDefaultFilters = false)
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(Config.class)
public class AdminWebContent {


	public static void main(String[] args) {
        SpringApplication.run(AdminWebContent.class, args);
    }
	
}
