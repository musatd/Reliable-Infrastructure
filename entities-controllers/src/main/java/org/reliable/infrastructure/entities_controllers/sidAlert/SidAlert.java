package org.reliable.infrastructure.entities_controllers.sidAlert;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sid_alert")
public class SidAlert {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsidalert")
    private Long idSidAlert;
	
	@Basic(optional = false)
    @Column(name = "sid")
    private String sid;
	
	@Basic(optional = false)
	@Column(name = "idalert")
	private Long idalert;

	public SidAlert() {}
	
	public SidAlert(Long idSidAlert, String sid, Long idalert) {
		this.idSidAlert = idSidAlert;
		this.sid = sid;
		this.idalert = idalert;
	}

	public Long getIdSidAlert() {
		return idSidAlert;
	}

	public void setIdSidAlert(Long idSidAlert) {
		this.idSidAlert = idSidAlert;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Long getIdalert() {
		return idalert;
	}

	public void setIdalert(Long idalert) {
		this.idalert = idalert;
	}

	@Override
	public String toString() {
		return "SidAlert [idSidAlert=" + idSidAlert + ", sid=" + sid + ", idalert=" + idalert + "]";
	}
	
}
