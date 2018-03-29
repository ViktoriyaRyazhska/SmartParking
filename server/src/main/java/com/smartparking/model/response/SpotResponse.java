package com.smartparking.model.response;

public class SpotResponse {

    private Long id;

    private Boolean isFree;

    public Boolean getIsFree() {
        return isFree;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

}
