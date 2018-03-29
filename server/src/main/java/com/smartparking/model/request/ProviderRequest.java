package com.smartparking.model.request;

import com.smartparking.entity.Provider;

public class ProviderRequest {
    private Long id;
    private String name;
    private String city;
    private String street;
    private String building;
    private boolean active;

    public Provider toProvider() {
        Provider provider = new Provider();
        provider.setId(id);
        provider.setName(name);
        provider.setCity(city);
        provider.setStreet(street);
        provider.setBuilding(building);
        provider.setActive(active);
        return provider;
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

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public boolean getActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

}
