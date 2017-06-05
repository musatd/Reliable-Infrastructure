package org.reliable.infrastructure.entities_controllers.util;

public class ACKData {
    private Long idAlert;
    private String token;

    public ACKData() {}

    public ACKData(Long idAlert, String token) {
        this.idAlert = idAlert;
        this.token = token;
    }

    public Long getIdAlert() {
        return idAlert;
    }

    public void setIdAlert(Long idAlert) {
        this.idAlert = idAlert;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
