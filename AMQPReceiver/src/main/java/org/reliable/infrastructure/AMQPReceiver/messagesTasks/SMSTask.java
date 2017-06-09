package org.reliable.infrastructure.AMQPReceiver.messagesTasks;

import java.util.List;
import java.util.logging.Logger;

import org.reliable.infrastructure.AMQPReceiver.Notifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class SMSTask extends Task {
	private static final String ACCOUNT_SID = "ACb523b993ffb15605a535912c6f07c288";
	private static final String AUTH_TOKEN = "055f88bb347345c2fee5184ce32dfa16f";
	private static final String FROM = "+18312641642";
	private static final String TO = "+15005550003";
	private static final String ADMIN_NUMBER = "+40747375387";
	private static final String STATUS_CALLBACK = "http://05a05911.ngrok.io/SMSStatus";

	private Logger logger = Logger.getLogger(SMSTask.class.getName());
	
	@Autowired
	AMQPReceiverService amqpReceiverService;
	

	@Override
	public void startTask() {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		List<String> phoneNumbers = notifications.getPhoneNumbers();
		String message = notifications.getMessage();
		int nrPhoneNumbers = phoneNumbers.size();
		for (int i = 0; i < nrPhoneNumbers; i++) {
			  try {
				  String to = phoneNumbers.get(i);
				  Message sms = Message.creator(new PhoneNumber(to), new PhoneNumber(FROM), message)
								  		.setStatusCallback(STATUS_CALLBACK)
									  	.create();

				  SidAlert sidAlert = new SidAlert();
				  sidAlert.setSid(sms.getSid());
				  sidAlert.setIdalert(notifications.getIdalert());
				  
				  amqpReceiverService.saveSidAlertAssociation(sidAlert);
			  } catch (ApiException e) {

				  Integer errorCode = e.getCode();
				  logger.warning("ErrorCode: " + errorCode + " and Message: " + e.getMessage());

				  if (Constants.USER_INVALID_NUMBER.equals(errorCode) || 
					  				Constants.USER_SMS_INCAPABLE.equals(errorCode)) {

					PushNotificationTask sendPushNotifToOneClient = new PushNotificationTask();
					sendPushNotifToOneClient.setSUBTITLE(Constants.USER_SUBTITLE_NOTIFICATION);
					Notifications notification = new Notifications();
					notification.setTokens(notifications.getTokens().subList(i, i + 1));
					notification.setMessage(Constants.USER_MESSAGE_NOTIFICATION);
					sendPushNotifToOneClient.setNotifications(notification);
					sendPushNotifToOneClient.startTask();

				  } else if (Constants.ADMIN_UNREACHABLE_CLIENT.equals(errorCode) ||
						  		Constants.ADMIN_PERMISSION_DISABLED.equals(errorCode)) {
					  
					  try {
					  		Message.creator(new PhoneNumber(ADMIN_NUMBER), new PhoneNumber(FROM), e.getMessage())
						  		.setStatusCallback(STATUS_CALLBACK)
							  	.create();
					  } catch (ApiException adminException) {
						  logger.warning("ErrorCode: " + adminException.getCode() + " and Message: " + adminException.getMessage());
					  }
				  }
			  } catch (Exception e) {
				  logger.warning(e.getMessage());
			  }
		}
	}
	
	@Override
	public String toString() {
		return "SMSTask [notifications=" + notifications + "]";
	}

}
