package org.reliable.infrastructure.AMQPSender;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = NotificationsDeserializer.class)
public class Notifications {
	private Integer idalert;
	private String notificationType;
	private String message;
	private String priority;
	private List<String> tokens;
	private List<String> phoneNumbers;
	
	
	public Notifications() {}
	

	public Notifications(Integer idalert, String notificationType, String message, String priority, List<String> tokens,
																	List<String> phoneNumbers) {
		this.idalert = idalert;
		this.notificationType = notificationType;
		this.message = message;
		this.priority = priority;
		this.tokens = tokens;
		this.phoneNumbers = phoneNumbers;
	}



	public Integer getIdalert() {
		return idalert;
	}

	public void setIdalert(Integer idalert) {
		this.idalert = idalert;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public List<String> getTokens() {
		return tokens;
	}


	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}


	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}


	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}


	@Override
	public String toString() {
		return "Notifications [idalert=" + idalert + ", notificationType=" + notificationType + ", message=" + message
				+ ", priority=" + priority + ", tokens=" + tokens + ", phoneNumbers=" + phoneNumbers + "]";
	}
	
	
}
