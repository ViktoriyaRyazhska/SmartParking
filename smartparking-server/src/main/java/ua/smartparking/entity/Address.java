package com.smartparking.entity.v2;

import com.smartparking.entity.BuildingNumber;
import com.smartparking.entity.City;
import com.smartparking.entity.Parking;
import com.smartparking.entity.Street;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "address")
public class Address extends AbstractEntityId {

    @NotNull
    @Column(name = "state", nullable = false)
    private String state;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "building_number")
    private String buildingNumber;

    @OneToMany(mappedBy = "address")
    private List<Parking> parkings;

    public List<Parking> getParkings() {
        return parkings;
    }

    public void setParkings(List<Parking> parkings) {
        this.parkings = parkings;
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
