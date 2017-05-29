package org.reliable.infrastructure.admin_web_content.controllers;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Util {
	private static final String ENCODED_LOW_PRIORITY = "Low";
	private static final String ENCODED_NORMAL_PRIORITY = "Normal";
	private static final String ENCODED_HIGH_PRIORITY = "High";
	private static final String DECODED_LOW_PRIORITY = "1";
	private static final String DECODED_NORMAL_PRIORITY = "2";
	private static final String DECODED_HIGH_PRIORITY = "3";
	private static final String DEFAULT_PRIORITY = "0";
	
	
	public String appendCitiesToMessage(String message, List<String> cities) {
		StringBuilder finalMessage = new StringBuilder();
		finalMessage.append(message);
		finalMessage.append(" pentru orasele:");
		int nrCities = cities.size();
		
		for (int i = 0; i < nrCities - 1; i++) {
			finalMessage.append(" " + cities.get(i) + ",");
		}
		finalMessage.append(" " + cities.get(nrCities - 1) + ".");
		
		return finalMessage.toString();
	}
	
	public String convertPriority(String priority) {
		
		if (priority.equals(ENCODED_LOW_PRIORITY)) {
			return DECODED_LOW_PRIORITY;
		} else if (priority.equals(ENCODED_NORMAL_PRIORITY)) {
			return DECODED_NORMAL_PRIORITY;
		} else if (priority.equals(ENCODED_HIGH_PRIORITY)) {
			return DECODED_HIGH_PRIORITY;
		} else {
			return DEFAULT_PRIORITY;
		}
	}

}
