package org.reliable.infrastructure.entities_controllers.client;

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


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "client")
public class Client {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idclient")
    private Long idclient;
	
	@Basic(optional = false)
    @Column(name = "token")
    private String token;
	
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;
    
    
    @JoinTable
        (name = "client_city"
            , 
            joinColumns = { 
                @JoinColumn(name = "client_idclient", referencedColumnName = "idclient")
            }, 
            inverseJoinColumns = { 
                @JoinColumn(name = "city_idcity", referencedColumnName = "idcity")
            }
        )
    @ManyToMany
    @JsonIgnore
    private List<City> cities;
    
    @OneToMany(mappedBy = "pk.client", cascade=CascadeType.ALL, orphanRemoval=true)
    @JsonIgnore
	private List<AlertClient> alertClients;
    
    public Client() {}

    public Client(Long idclient) {
        this.idclient = idclient;
    }

    public Client(Long idclient, String token, String phone) {
        this.idclient = idclient;
        this.token = token;
        this.phone = phone;
    }
    
    
    public Long getIdclient() {
		return idclient;
	}

	public void setIdclient(Long idclient) {
		this.idclient = idclient;
	}

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
	

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idclient != null ? idclient.hashCode() : 0);
        return hash;
    }

	@Override
	public String toString() {
		return "Client [idclient=" + idclient + ", token=" + token + ", phone=" + phone + " "  
				+ ", alertClients=" + alertClients + "]";
	}
	
	
}
