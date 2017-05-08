package org.reliable.infrastructure.entities_controllers.util;

import java.util.List;

import org.reliable.infrastructure.entities_controllers.alert.Alert;
import org.reliable.infrastructure.entities_controllers.client.Client;

public class WorkerThread implements Runnable{
	
	private List<AlertClient> alertClient;
	private List<Client> clients;
	private Alert alert;
	private int threadNumber;
	private int chunkSize;
	
	public WorkerThread(List<AlertClient> alertClient, List<Client> clients, Alert alert, int threadNumber, int chunkSize) {
		this.alertClient = alertClient;
		this.clients = clients;
		this.alert = alert;
		this.threadNumber = threadNumber;
		this.chunkSize = chunkSize;
	}
	
	@Override
	public void run() {
		int startIndex = chunkSize * threadNumber;
		int stopIndex = startIndex + chunkSize;
		stopIndex = stopIndex > clients.size() ? clients.size() : stopIndex;
		
		for (int i = startIndex; i < stopIndex; i++) {
			AlertClient newAlertClient = new AlertClient();
			newAlertClient.setAlert(alert);
			newAlertClient.setClient(clients.get(i));
			newAlertClient.setStatus(Util.STATUS_INITIALIZE);
			alertClient.add(i, newAlertClient);
		}
		
	}
	

}
