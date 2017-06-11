package org.reliable.infrastructure.AMQPReceiver.messagesTasks;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.http.HttpMethod;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;

@Component
public class CallTask extends Task {
	private static final String ACCOUNT_SID = "ACb523b993ffb15605a535912c6f07c288";
	private static final String AUTH_TOKEN = "55f88bb347345c2fee5184ce32dfa16f";
	private static final String FROM = "+18312641642";
	private static final String STATUS_CALLBACK = "http://9b31360b.ngrok.io/CallStatus";
	private static final String CALL_MESSAGE = "http://9b31360b.ngrok.io/sidAlert/mobileVoice";
	
	private Logger logger = Logger.getLogger(CallTask.class.getName());
	
	@Autowired
	AMQPReceiverService amqpReceiverService;

	@Override
	public void startTask() {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		List<String> callbackEvents = Arrays.asList("ringing");
		List<String> phoneNumbers = notifications.getPhoneNumbers();
		int nrPhoneNumbers = phoneNumbers.size();
		for (int i = 0; i < nrPhoneNumbers; i++) {
	      String to = phoneNumbers.get(i);
		  Call call = null;
			try {
				call = Call.creator(new PhoneNumber(to), new PhoneNumber(FROM),
					        new URI(CALL_MESSAGE))
						  	.setStatusCallback(STATUS_CALLBACK)
						  	.setStatusCallbackMethod(HttpMethod.POST)
						  	.setStatusCallbackEvent(callbackEvents)
						  	.create();
			} catch (ApiException | URISyntaxException e) {
				logger.warning(e.getMessage());
				e.printStackTrace();
				continue;
			}

			  SidAlert sidAlert = new SidAlert();
			  sidAlert.setSid(call.getSid());
			  sidAlert.setIdalert(notifications.getIdalert());
			  
			  amqpReceiverService.saveSidAlertAssociation(sidAlert);
		}
		
	}
	
	
	@Override
	public String toString() {
		return "CallTask [notifications=" + notifications + "]";
	}
}
