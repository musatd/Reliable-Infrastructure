package org.reliable.infrastructure.entities_controllers.alert;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.reliable.infrastructure.entities_controllers.city.City;
import org.reliable.infrastructure.entities_controllers.city.CityRepository;
import org.reliable.infrastructure.entities_controllers.client.Client;
import org.reliable.infrastructure.entities_controllers.client.ClientRepository;
import org.reliable.infrastructure.entities_controllers.util.ACKData;
import org.reliable.infrastructure.entities_controllers.util.AlertClient;
import org.reliable.infrastructure.entities_controllers.util.EntitiesService;
import org.reliable.infrastructure.entities_controllers.util.Util;
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
	private EntitiesService entitiesService;
	
	@Autowired
	private Util util;
	
	
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
		
		if (subscribedClients.size() == 0) {
			logger.info("There is no client subscribed to any region of this alert");
			logger.info("We do not save the alert to database since we do not have any receivers for it");
			return true;
		}
		
		logger.info("UtilMethods createAlertClientAssociations() invoked");
		List<AlertClient> notifications = util.createAlertClientAssociations(alert, subscribedClients);
		alert.setAlertClients(notifications);
		
		logger.info("alertRepository save() invoked: " + alertRepository);
		Alert savedAlert = alertRepository.save(alert);
		logger.info("alertRepository save() found: " + savedAlert);

		if (savedAlert == null) {
			return false;
		}
		
		entitiesService.broadcastNotifications(savedAlert);
		
		return true;
	}
	
	
	/**
	 * Retrieve all alerts that a client subscribed for and send them
	 * to the mobile application of the client
	 * 
	 * @param token of the client, used to retrieve alerts associated with token
	 * @return alerts from the cities the client subscribed to
	 */
	@RequestMapping(value = "/alerts/getClientAlerts", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getClientAlerts(@RequestBody String token) {
		token = token.substring(1, token.length() - 1);
		logger.info("received token: " + token);
		
		List<Alert> alerts = alertRepository.getClientAlerts(token);
		
		logger.info("for received token the next alerts were found: " + alerts);
		int size = alerts.size();
		List<String> alertMessages = new ArrayList<String>();
		
		for (int i = 0; i < size; i++) {
			alertMessages.add(alerts.get(i).getMessage());
		}
		
		return alertMessages;
	}
	
	
	/**
	 * The function receives acknowledge of the alert sent by the client and delete 
	 * that entry from AlertClient database table
	 * 
	 * @param ackData represents the information sent by the client as part of acknowledge 
	 * 			and which is needed to delete the alert-client entry from AlertClient database table
	 */
	@RequestMapping(value = "/alerts/sendACK")
	public void receiveACK(@RequestBody ACKData ackData) {
		String token = ackData.getToken();
		logger.info("clientRepository byToken() invoked: " + token);
		Client foundClient = clientRepository.findByToken(token);
		logger.info("clientRepository byToken() found: " + foundClient);
		
		if (foundClient == null) {
			logger.info("No client with token: " + token + " was found");
			return;
		}
		
		Long idAlert = ackData.getIdAlert();
		Long idClient = foundClient.getIdclient();
		
		alertRepository.deleteAlertClient(idAlert, idClient);
		
		logger.info("The entry from AlertClient database table with idAlert: " + idAlert + 
						" and idClient: " + idClient + " was deleted");
	}
	
/*
		//@ResponseStatus(value = HttpStatus.SC_NO_CONTENT)
	@RequestMapping(value = "/SMSStatus", method = RequestMethod.POST, produces = "text/xml")
	@ResponseBody
	public String processSms(@RequestParam(value = "MessageStatus", required = false) String messageStatus,
	                       @RequestParam(value = "ApiVersion", required = false) String apiVersion,
	                       @RequestParam(value = "SmsSid", required = false) String smsSid,
	                       @RequestParam(value = "SmsStatus", required = false) String smsStatus,
	                       @RequestParam(value = "To", required = false) String to,
	                       @RequestParam(value = "From", required = false) String from,
	                       @RequestParam(value = "MessageSid", required = false) String messageSid,
	                       @RequestParam(value = "AccountSid", required = false) String accountSid) {
		
		logger.info("MessageStatus: " + messageStatus);
		logger.info("ApiVersion: " + apiVersion);
		logger.info("SmsSid: " + smsSid);
		logger.info("SmsStatus: " + smsStatus);
		logger.info("To: " + to);
		logger.info("From: " + from);
		logger.info("MessageSid: " + messageSid);
		logger.info("AccountSid: " + accountSid);
	/*
	    TwilioCallBackResponse response = new TwilioCallBackResponse();
	    response.messageStatus = messageStatus;
	    response.apiVersion = apiVersion;
	    response.smsSid = smsSid;
	    response.smsStatus = smsStatus;
	    response.to = to;
	    response.from = from;
	    response.messageSid = messageSid;
	    response.accountSid = accountSid;
	
	    //LOG.info("Incomming twilio callback: " + JsonUtils.printJson(response));
	
	    //smsService.processSmsCallback(response);
	
	    return "<Response/>";
	}
*/
}
