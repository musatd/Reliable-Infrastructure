package org.reliable.infrastructure.entities_controllers.util;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.reliable.infrastructure.entities_controllers.alert.Alert;
import org.reliable.infrastructure.entities_controllers.client.Client;

@Embeddable
public class AlertClientPk implements Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Alert alert;
	
	@ManyToOne
	private Client client;
	

	public Alert getAlert() {
		return alert;
	}

	public void setAlert(Alert alert) {
		this.alert = alert;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
