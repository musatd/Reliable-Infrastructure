package org.reliable.infrastructure.admin_web_content.controllers.util;

import java.util.ArrayList;

public class User {
	private String token;
	private String phone;
	private ArrayList<String> cities;
	
	public User() {}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public ArrayList<String> getCities() {
		return cities;
	}

	public void setCities(ArrayList<String> cities) {
		this.cities = cities;
	}

	@Override
	public String toString() {
		return "User [token=" + token + ", phone=" + phone + ", cities=" + cities + "]";
	}

	
}
