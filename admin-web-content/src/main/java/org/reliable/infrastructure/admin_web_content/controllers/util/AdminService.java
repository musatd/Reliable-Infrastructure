package org.reliable.infrastructure.admin_web_content.controllers.util;

import java.util.List;
import java.util.logging.Logger;

import org.reliable.infrastructure.admin_web_content.controllers.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.client.RestTemplate;

public class AdminService {
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	@Autowired
	private Util util;

	private String serviceUrl;

	private Logger logger = Logger.getLogger(AdminService.class.getName());

	public AdminService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}
	
	/**
	 * The function receives an user and sends it to entities_controllers to be saved
	 * @param user contains the information that has to be saved
	 * @return True if the user was successfully saved, False otherwise
	 */
	public Boolean saveUser(User user) {

		logger.info("saveClient() invoked: for " + user);
		Boolean isSaved = restTemplate.postForObject(serviceUrl + "/clients/saveClient",
														user, Boolean.class);
		logger.info("saveClient() found: " + isSaved);
		
		return isSaved;
	}
	
	
	/**
	 * Sends token to the entities-controllers to delete the client associated with it
	 * @param token is a string unique for each client
	 * @return True if the user was successfully deleted, False otherwise
	 */
	public Boolean deleteUser(String token) {
		
		logger.info("deleteClient() invoked: for " + token);
		Boolean isDeleted = restTemplate.postForObject(serviceUrl + "/clients/deleteClient",
														token, Boolean.class);
		logger.info("deleteClient() found: " + isDeleted);
		
		return isDeleted;
	}
	
	
	/**
	 * The function sends the message to entities-controllers in order to be stored to database
	 * @param message contains information of alert
	 * @return True if the alert was successfully created, False otherwise
	 */
	public Boolean createMessage(Message message) {
		
		logger.info("add cities to alert message");
		String alertMessage = message.getAlertMessage();
		List<String> cities = message.getCities();
		String updatedMessage = util.appendCitiesToMessage(alertMessage, cities);
		message.setAlertMessage(updatedMessage);
		logger.info("message updated to: " + updatedMessage);
		
		logger.info("convert priority to a digit");
		String decodedPriority = util.convertPriority(message.getPriority());
		message.setPriority(decodedPriority);
		logger.info("priority converted to: " + decodedPriority);
		
		logger.info("createMessage() invoked: for " + message);
		Boolean isSaved = restTemplate.postForObject(serviceUrl + "/alerts/createAlert",
														message, Boolean.class);
		logger.info("createMessage() found: " + isSaved);
		
		return isSaved;
	}

}
