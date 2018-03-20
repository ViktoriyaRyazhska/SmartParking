package com.smartparking.model.response;

import com.smartparking.entity.Parking;
import com.smartparking.entity.Provider;

import java.util.List;
import java.util.stream.Collectors;

public class ProviderItemResponse {
    private Long id;
    private String name;
    private String address;
    private Boolean active;

    public static ProviderItemResponse of(Provider provider){
        ProviderItemResponse providerResponse = new ProviderItemResponse();
        providerResponse.setId(provider.getId());
        providerResponse.setName(provider.getName());
        providerResponse.setAddress(provider.getLegalAddress().getCity() +
                ", " + provider.getLegalAddress().getStreet() +
                ", " + provider.getLegalAddress().getBuildingNumber());
        providerResponse.setActive(provider.getActive());
        return providerResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
