package org.reliable.infrastructure.AMQPReceiver.messagesTasks;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.client.RestTemplate;

public class AMQPReceiverService {

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	private String serviceUrl;

	private Logger logger = Logger.getLogger(AMQPReceiverService.class.getName());

	public AMQPReceiverService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}
	
	
	public void saveSidAlertAssociation(SidAlert sidAlert) {
		logger.info("saveAssocation from entities-controllers invoked: for " + sidAlert);
		restTemplate.postForObject(serviceUrl + "/sidAlert/saveAssocation", sidAlert, Void.class);
		logger.info("saveAssocation from entities-controllers ended");
	}
}
