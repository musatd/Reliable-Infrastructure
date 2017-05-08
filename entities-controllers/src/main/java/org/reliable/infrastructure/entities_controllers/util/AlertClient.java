package org.reliable.infrastructure.entities_controllers.util;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.reliable.infrastructure.entities_controllers.alert.Alert;
import org.reliable.infrastructure.entities_controllers.client.Client;

@Entity
@Table(name ="alert_client")
@AssociationOverrides({
@AssociationOverride(name ="pk.alert", joinColumns = @JoinColumn(name ="alert_idalert")),
@AssociationOverride(name ="pk.client", joinColumns = @JoinColumn(name ="client_idclient"))
        })
public class AlertClient {
	
	@EmbeddedId
	private AlertClientPk pk = new AlertClientPk();
	
	@Basic(optional = false)
    @Column(name = "status")
	private String status;
	
	public AlertClient() {
	}
	
	@Transient
	public Alert getAlert() {
		return pk.getAlert();
	}
	
	public void setAlert(Alert alert) {
		pk.setAlert(alert);
	}
	
	@Transient
	public Client getClient() {
		return pk.getClient();
	}
	
	public void setClient(Client client) {
		pk.setClient(client);
	}
	

	public String getStatus() {
		return status;
	}
	

	public void setStatus(String status) {
		this.status = status;
	}
	
}
