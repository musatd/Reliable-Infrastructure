package org.reliable.infrastructure.entities_controllers.alert;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.reliable.infrastructure.entities_controllers.city.City;
import org.reliable.infrastructure.entities_controllers.util.AlertClient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "alert")
@JsonIdentityInfo(
generator = ObjectIdGenerators.PropertyGenerator.class,
property = "idalert",
scope = Alert.class)
public class Alert {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idalert")
	private Long idalert;
		
	@Basic(optional = false)
	@Column(name = "timestamp")
	private Timestamp timestamp;
	
	@Basic(optional = false)
	@Column(name = "message")
	private String message;
	
	@Basic(optional = false)
	@Column(name = "priority")
	private String priority;
	
	
	@JoinTable
    (name = "alert_city"
        , 
        joinColumns = { 
            @JoinColumn(name = "alert_idalert", referencedColumnName = "idalert")
        }, 
        inverseJoinColumns = { 
            @JoinColumn(name = "city_idcity", referencedColumnName = "idcity")
        }
    )
	@ManyToMany
	private List<City> cities;
	
	@OneToMany(mappedBy = "pk.alert", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<AlertClient> alertClients;
	
	public Alert() {}
	
	public Alert(Long idalert) {
        this.idalert = idalert;
    }

    public Alert(Long idalert, Timestamp timestamp, String message, String priority) {
        this.idalert = idalert;
        this.timestamp = timestamp;
        this.message = message;
        this.priority = priority;
    }

	public Long getIdalert() {
		return idalert;
	}

	public void setIdalert(Long idalert) {
		this.idalert = idalert;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
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

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public List<AlertClient> getAlertClients() {
		return alertClients;
	}

	public void setAlertClients(List<AlertClient> alertClients) {
		this.alertClients = alertClients;
	}
	
	
}
