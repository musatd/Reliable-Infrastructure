package org.reliable.infrastructure.entities_controllers.sidAlert;

import java.util.logging.Logger;

import javax.xml.soap.SOAPException;

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

import com.twilio.twiml.Play;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;

@RestController
public class SidAlertController {
	
	private Logger logger = Logger.getLogger(SidAlertController.class.getName());
	private static final String CALL_MESSAGE = "We were not able to reach you via push notification"
												+ " and SMS. Please check your application's settings";

	private static final String RECORDED_MESSAGE = "http://9b31360b.ngrok.io/alerta.mp3";
	
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
		
		return deleteEntry(smsSid, to);
	}
	
	
	/**
	 * Build the message which will be used by twilio in the phone call
	 * @return the information to be played to the client
	 * @throws SOAPException
	 * @throws TwiMLException
	 */
	@RequestMapping(value = "/sidAlert/mobileVoice", produces=MediaType.APPLICATION_XML_VALUE)
	public String mobileVoiceMessage() throws SOAPException, TwiMLException {
		VoiceResponse voiceTwimlResponse = new VoiceResponse.Builder()
                //.say(new Say.Builder(CALL_MESSAGE).voice(Voice.MAN).language(Language.EN).build())
				.play(new Play.Builder(RECORDED_MESSAGE).build())
                .build();
		
		return voiceTwimlResponse.toXml();
	}
	
	
	/**
	 * Receives call statuses along its way to the recipient
	 * @param callSid unique id of the call
	 * @param callStatus current status of the call
	 * @param to the recipient of the call
	 * @param from the initiator of the call
	 * @return an empty message
	 */
	@RequestMapping(value = "/CallStatus", method = RequestMethod.POST, produces = "text/xml")
	@ResponseBody
	public String processCall(@RequestParam(value = "CallSid", required = false) String callSid,
	                       @RequestParam(value = "CallStatus", required = false) String callStatus,
	                       @RequestParam(value = "To", required = false) String to,
	                       @RequestParam(value = "From", required = false) String from) {
		
		logger.info("CallStatus: " + callStatus);
		logger.info("CallSid: " + callSid);
		logger.info("To: " + to);
		logger.info("From: " + from);
		
		if (!callStatus.equals("completed") && !callStatus.equals("busy")) {
			return "<Response/>"; 
		}
		
		
		
		return deleteEntry(callSid, to);
	}
	
	
	private String deleteEntry(String sid, String toPhoneNumber) {
		
		SidAlert sidAlert = sidAlertRepository.findBySid(sid);
		if (sidAlert == null) {
			logger.info("No entry with sid: " + sid + " was found");
			return "<Response/>";
		}
		
		Client client = clientRepository.findByPhone(toPhoneNumber);
		if (client == null) {
			logger.info("No client with phoneNumber: " + toPhoneNumber + " was found");
		}
		
		Long idAlert = sidAlert.getIdalert();
		Long idClient = client.getIdclient();
		
		alertRepository.deleteAlertClient(idAlert, idClient);
		sidAlertRepository.deleteBySid(sid);
		
		logger.info("The entry from AlertClient database table with idAlert: " + idAlert + 
						" and idClient: " + idClient + " was deleted");
		
		return "<Response/>";
	}
	
	
}
