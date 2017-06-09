package org.reliable.infrastructure.AMQPReceiver.messagesTasks;

public class SidAlert {

    private String sid;
	
	private Long idalert;

	public SidAlert() {}
	
	public SidAlert( String sid, Long idalert) {
		this.sid = sid;
		this.idalert = idalert;
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
		return "SidAlert [sid=" + sid + ", idalert=" + idalert + "]";
	}
	
}
