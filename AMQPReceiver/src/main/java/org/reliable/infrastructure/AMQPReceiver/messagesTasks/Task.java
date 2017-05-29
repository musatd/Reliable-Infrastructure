package org.reliable.infrastructure.AMQPReceiver.messagesTasks;

import org.reliable.infrastructure.AMQPReceiver.Notifications;

public abstract class Task {
	protected Notifications notifications;
	
	public abstract void startTask();
	
	public Notifications getNotifications() {
		return notifications;
	}


	public void setNotifications(Notifications notifications) {
		this.notifications = notifications;
	}


	@Override
	public String toString() {
		return "PushNotificationTask [notifications=" + notifications + "]";
	}
}
