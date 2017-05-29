package org.reliable.infrastructure.AMQPSender;

import org.springframework.stereotype.Component;

@Component
public class Util {
	private static final Integer PUSH_CHUNK_SIZE = 5000;
	private static final Integer SMS_CHUNK_SIZE = 1000;
	private static final Integer CALL_CHUNK_SIZE = 100;
	private static final String PUSH_NOTIFICATIONS_CODE = "0";
	private static final String SMS_CODE = "1";
	private static final String CALL_CODE = "2";
	
	public int getChunkSize(String type) throws Exception {
		
		if (type.equals(PUSH_NOTIFICATIONS_CODE)) {
			return PUSH_CHUNK_SIZE;
		} else if (type.equals(SMS_CODE)) {
			return SMS_CHUNK_SIZE;
		} else if (type.equals(CALL_CODE)) {
			return CALL_CHUNK_SIZE;
		} else {
			throw new Exception("This type: " + type + " is not recognized");
		}
	}
	
	
}
