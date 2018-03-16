package com.smartparking.dto;

import com.smartparking.entity.Spot;

public class SpotDto {

    private Long id;

    private Boolean isFree;

    public Boolean getFree() {
        return isFree;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }

}
