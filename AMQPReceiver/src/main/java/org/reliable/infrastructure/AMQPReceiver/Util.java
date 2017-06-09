package org.reliable.infrastructure.AMQPReceiver;

import org.reliable.infrastructure.AMQPReceiver.messagesTasks.CallTask;
import org.reliable.infrastructure.AMQPReceiver.messagesTasks.PushNotificationTask;
import org.reliable.infrastructure.AMQPReceiver.messagesTasks.SMSTask;
import org.reliable.infrastructure.AMQPReceiver.messagesTasks.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Util {
	public static final String QUEUE_NAME = "Reliable-Test";
	private static final String PUSH_NOTIFICATIONS_CODE = "0";
	private static final String SMS_CODE = "1";
	private static final String CALL_CODE = "2";
	
	@Autowired
	PushNotificationTask pushNotificationTask;
	
	@Autowired
	SMSTask smsTask;
	
	@Autowired
	CallTask callTask;
	
	
	public Task taskFactory(String type) throws Exception {
		
		if (type.equals(PUSH_NOTIFICATIONS_CODE)) {
			return pushNotificationTask;
		} else if (type.equals(SMS_CODE)) {
			return smsTask;
		} else if (type.equals(CALL_CODE)) {
			return callTask;
		} else {
			throw new Exception("This type: " + type + " is not recognized");
		}
		
	}
}
