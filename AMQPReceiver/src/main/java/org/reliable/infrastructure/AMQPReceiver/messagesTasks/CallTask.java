package org.reliable.infrastructure.AMQPReceiver.messagesTasks;

import org.springframework.stereotype.Component;

@Component
public class CallTask extends Task {

	@Override
	public void startTask() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public String toString() {
		return "CallTask [notifications=" + notifications + "]";
	}
}
