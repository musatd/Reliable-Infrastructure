package org.reliable.infrastructure.admin_web_content.controllers.util;

import java.util.ArrayList;

public class Message {
	private String alertMessage;
	private String priority;
	private ArrayList<String> cities;

	public Message() {}

	
	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	

	public ArrayList<String> getCities() {
		return cities;
	}


	public void setCities(ArrayList<String> cities) {
		this.cities = cities;
	}


	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}


	@Override
	public String toString() {
		return "Message [alertMessage=" + alertMessage + ", cities=" + cities + ", priority=" + priority + "]";
	}

	
	
}
