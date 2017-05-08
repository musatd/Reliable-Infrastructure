package org.reliable.infrastructure.entities_controllers.alert;

import java.util.List;
import java.util.logging.Logger;

import org.reliable.infrastructure.entities_controllers.city.City;
import org.reliable.infrastructure.entities_controllers.city.CityRepository;
import org.reliable.infrastructure.entities_controllers.client.Client;
import org.reliable.infrastructure.entities_controllers.client.ClientRepository;
import org.reliable.infrastructure.entities_controllers.util.AlertClient;
import org.reliable.infrastructure.entities_controllers.util.UtilMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlertController {
	
	private Logger logger = Logger.getLogger(AlertController.class.getName());
	
	@Autowired
	private AlertRepository alertRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private UtilMethods clientUtil;
	
	/**
	 * Save the alert in the database and the 
	 * notifications that have to be sent to the subscribers.
	 * 
	 * @param message contains the information to be saved to database
	 * @return True if alert was successfully saved, False otherwise
	 * @throws Exception 
	 */
	@RequestMapping(value = "/alerts/createAlert", produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean createAlert(@RequestBody Message message) throws Exception {

		List<String> cities = message.getCities();
		Alert alert = new Alert();
		alert.setMessage(message.getAlertMessage());
		alert.setPriority(message.getPriority());
		
		
		if (cities != null) {
			logger.info("cityRepository getByNameInList() invoked: for " + cities);
			List<City> alertedCities = cityRepository.getByNameInList(cities);
			logger.info("cityRepository getByNameInList() found: " + alertedCities);
			
			alert.setCities(alertedCities);
		} else {
			throw new Exception("No city associated with the message: " + message);
		}
		
		logger.info("clientRepository findClientsSubscribedToCities() invoked for: " + cities);
		List<Client> subscribedClients = clientRepository.findClientsSubscribedToCities(cities);
		logger.info("clientRepository findClientsSubscribedToCities() found: " + subscribedClients);
		
		logger.info("UtilMethods createAlertClientAssociations() invoked");
		List<AlertClient> notifications = clientUtil.createAlertClientAssociations(alert, subscribedClients);
		alert.setAlertClients(notifications);
		
		logger.info("alertRepository save() invoked: " + alertRepository);
		Alert savedAlert = alertRepository.save(alert);
		logger.info("alertRepository save() found: " + savedAlert);
		
		return savedAlert == null ? false : true;
	}

}
