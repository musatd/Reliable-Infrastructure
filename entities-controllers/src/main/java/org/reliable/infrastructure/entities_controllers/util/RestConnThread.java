package org.reliable.infrastructure.entities_controllers.util;

import java.util.logging.Logger;

import org.reliable.infrastructure.entities_controllers.alert.Alert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class RestConnThread extends Thread {

	private Logger logger = Logger.getLogger(RestConnThread.class.getName());
	private Alert alert;
	private RestTemplate restTemplate;
	private String serviceUrl;
	
	public RestConnThread(Alert alert, RestTemplate restTemplate, String serviceUrl) {
		this.alert = alert;
		this.restTemplate = restTemplate;
		this.serviceUrl = serviceUrl;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Alert> entity = new HttpEntity<Alert>(alert, headers);
		logger.info("restTemplate invoked: for " + alert);
		restTemplate.exchange(serviceUrl + "/amqpSender/sendAlerts", HttpMethod.POST, entity, Void.class);
		logger.info("restTemplate finised successfully");
	}
}
