package org.reliable.infrastructure.entities_controllers.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.reliable.infrastructure.entities_controllers.alert.Alert;
import org.reliable.infrastructure.entities_controllers.city.City;
import org.reliable.infrastructure.entities_controllers.city.CityRepository;
import org.reliable.infrastructure.entities_controllers.client.Client;
import org.reliable.infrastructure.entities_controllers.client.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Util {
	
	@Autowired
	private CityRepository cityRepository;
	
	public static final String STATUS_INITIALIZE = "0";
	public static final String STATUS_PUSH = "1";
	public static final String STATUS_SMS = "2";
	public static final String STATUS_CALL = "3";
	public static final String STATUS_END = "4";
	
	private static final String NUMBER_PREFIX = "+4";
	
	private Logger logger = Logger.getLogger(Util.class.getName());
	private static final Integer CHUNK = 1000;
	private static final Integer NR_THREADS = 5;
	
	/**
	 * Retrieve from database the cities that user have subscribed to and
	 * save them into client object along with token and phone.
	 * @param client to be saved in database
	 * @param user received from the web application
	 */
	public void prepareClientForSave(Client client, User user) {
		client.setToken(user.getToken());
		client.setPhone(NUMBER_PREFIX + user.getPhone());
		List<String> selectedRegions = user.getCities();
		
		if (selectedRegions != null && !selectedRegions.isEmpty()) {
			logger.info("cityRepository getByNameInList() invoked: for " + selectedRegions);
			List<City> subscribedCities = cityRepository.getByNameInList(selectedRegions);
			logger.info("cityRepository getByNameInList() found: " + subscribedCities);
			
			client.setCities(subscribedCities);
		} else {
			client.setCities(new ArrayList<City>());
		}
	}
	
	
	/**
	 * Make associations between the created alert and all the users that have to receive
	 * the alert.
	 * @param alert to be saved to database
	 * @param subscribedClients that have to receive the message of alert
	 * @return a list of associations
	 */
	public List<AlertClient> createAlertClientAssociations(Alert alert, List<Client> subscribedClients) {
		int clientsNumber = subscribedClients.size();
		List<AlertClient> alertClient = new ArrayList<AlertClient>(clientsNumber);
		ExecutorService executor = Executors.newFixedThreadPool(NR_THREADS);
		
		int divideResult = clientsNumber / CHUNK;
		int tasksNumber = clientsNumber % CHUNK == 0 ? divideResult : divideResult + 1;
		
		for (int i = 0; i < tasksNumber; i++) {
			Runnable worker = new WorkerThread(alertClient, subscribedClients, alert, i, CHUNK);
			executor.execute(worker);
		}
		
		executor.shutdown();
        while (!executor.isTerminated());

		
		return alertClient;
	}
	
	
	public String getNextStatus(String status) throws Exception {
		
		if (status.equals(STATUS_INITIALIZE)) {
			return STATUS_PUSH;
		} else if (status.equals(STATUS_PUSH)) {
			return STATUS_SMS;
		} else if (status.equals(STATUS_SMS)) {
			return STATUS_CALL;
		} else if (status.equals(STATUS_CALL)) {
			return STATUS_END;
		} else {
			throw new Exception("This status: " + status + " is unknown");
		}
	}

}
