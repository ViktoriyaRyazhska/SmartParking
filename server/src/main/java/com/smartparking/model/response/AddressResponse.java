package com.smartparking.model.response;

import com.smartparking.entity.Address;

public class AddressResponse {

    private Long id;
    private String state;
    private String city;
    private String street;
    private String buildingNumber;

    public AddressResponse(Address address) {
        this.id = address.getId();
        this.state = address.getState();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.buildingNumber = address.getBuildingNumber();
    }

    public static AddressResponse of(Address address) {
        return new AddressResponse(address);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }
}
