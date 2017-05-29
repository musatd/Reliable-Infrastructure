package org.reliable.infrastructure.entities_controllers.util;

import java.util.Calendar;
import java.util.logging.Logger;

import org.reliable.infrastructure.entities_controllers.alert.Alert;
import org.reliable.infrastructure.entities_controllers.alert.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

public class EntitiesService {
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	@Autowired
	private Util util;
	
	@Autowired
	private AlertRepository alertRepository;

	private String serviceUrl;

	private Logger logger = Logger.getLogger(EntitiesService.class.getName());

	public EntitiesService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}
	
	
	
	public void sendNotificationsToRabbitEntryPoint(Alert alert) throws JsonProcessingException {

		logger.info("RestConnThread is starting for alert: " + alert);
		RestConnThread restConn = new RestConnThread(alert, restTemplate, serviceUrl);
		restConn.start();
		logger.info("RescConnThread started in a separated thread");
	}
	
	
	public Boolean updateAlert(Alert alert) throws Exception {
		String status = alert.getAlertClients().get(0).getStatus();
		String updateStatus = util.getNextStatus(status);
		
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
    	
    	alertRepository.updateTimestamp(alert.getIdalert(), currentTimestamp);
    	alertRepository.updateStatus(updateStatus, alert.getAlertClients());
		
        return true;
    }

}
