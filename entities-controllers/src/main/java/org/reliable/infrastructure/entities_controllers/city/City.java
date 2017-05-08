package org.reliable.infrastructure.entities_controllers.city;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import org.reliable.infrastructure.entities_controllers.alert.Alert;
import org.reliable.infrastructure.entities_controllers.client.Client;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name = "city")
@JsonIdentityInfo(
generator = ObjectIdGenerators.PropertyGenerator.class,
property = "idcity",
scope = City.class)
public class City {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcity")
    private Long idcity;
	
	@Basic(optional = false)
    @Column(name = "name")
    private String name;
	
	
	@ManyToMany(mappedBy = "cities")
    private List<Client> clients;
	
	@ManyToMany(mappedBy = "cities")
	private List<Alert> alerts;
	
	
	public City() {
    }

    public City(Long idcity) {
        this.idcity = idcity;
    }

    public City(Long idcity, String name) {
        this.idcity = idcity;
        this.name = name;
    }
    
    public Long getIdcity() {
		return idcity;
	}

	public void setIdcity(Long idcity) {
		this.idcity = idcity;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlTransient
	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	

	public List<Alert> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcity != null ? idcity.hashCode() : 0);
        return hash;
    }

	@Override
	public String toString() {
		return "CityEntity [idcity=" + idcity + ", name=" + name + ", clients=" + clients + "]";
	}
	
	
}
