package org.reliable.infrastructure.entities_controllers.sidAlert;

import java.util.logging.Logger;

import org.reliable.infrastructure.entities_controllers.alert.AlertRepository;
import org.reliable.infrastructure.entities_controllers.client.Client;
import org.reliable.infrastructure.entities_controllers.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SidAlertController {
	
	private Logger logger = Logger.getLogger(SidAlertController.class.getName());
	
	@Autowired
	private SidAlertRepository sidAlertRepository;

	@Autowired
	private AlertRepository alertRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	/**
	 * This method saves the association between SMS sid and idalert to database table SidAlert.
	 * This association will be used in the processSMS method to find which alert a SMS is linked with
	 * @param sidAlert contains values of sid and idalert
	 */
	@RequestMapping(value = "/sidAlert/saveAssocation", produces = MediaType.APPLICATION_JSON_VALUE)
	public void saveSidAlertAssocation(@RequestBody SidAlert sidAlert) {
		sidAlertRepository.save(sidAlert);
	}
	
	
	/**
	 * This method receives information about the delivery status of the SMS messages sent
	 * via Twilio. The received information is used delete the SMS associated client from the
	 * database table AlertClient
	 * @param smsSid is the unique ID for any message successfully created by Twilio's API
	 * @param smsStatus represents the current status of the message
	 * @param to is the number of the recipient 
	 * @return an empty answer to Twilio request
	 */
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
		
		if (!smsStatus.equals("delivered")) {
			return "<Response/>"; 
		}
		
		SidAlert sidAlert = sidAlertRepository.findBySid(smsSid);
		if (sidAlert == null) {
			logger.info("No entry with sid: " + smsSid + " was found");
			return "<Response/>";
		}
		
		Client client = clientRepository.findByPhone(to);
		if (client == null) {
			logger.info("No client with phoneNumber: " + to + " was found");
		}
		
		Long idAlert = sidAlert.getIdalert();
		Long idClient = client.getIdclient();
		
		alertRepository.deleteAlertClient(idAlert, idClient);
		sidAlertRepository.deleteBySid(smsSid);
		
		logger.info("The entry from AlertClient database table with idAlert: " + idAlert + 
						" and idClient: " + idClient + " was deleted");
	
	    return "<Response/>";
	}
	
}
