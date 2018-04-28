package com.smartparking.model.request;

import com.smartparking.entity.Parking;
import com.smartparking.entity.Provider;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParkingRequest {

    private Long id;
    private String city;
    private String street;
    private String building;
    private Double latitude;
    private Double longitude;
    private BigDecimal price;
    private String token;
    private Long providerId;
    private Boolean hasCharger;
    private Boolean hasInvalid;
    private Boolean isCovered;

    public Parking toParking() {
        Parking parking = new Parking();
        parking.setId(id);
        parking.setCity(city);
        parking.setStreet(street);
        parking.setBuilding(building);
        parking.setLatitude(latitude);
        parking.setLongitude(longitude);
        parking.setPrice(price);
        parking.setToken(token);
        Provider provider = new Provider();
        provider.setId(providerId);
        parking.setProvider(provider);
        parking.setIsCovered(isCovered);
        parking.setHasInvalid(hasInvalid);
        parking.setHasCharger(hasCharger);
        return parking;
    }

    @Override
    public String toString() {
        return "ParkingRequest{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", building='" + building + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", price=" + price +
                ", token='" + token + '\'' +
                ", providerId=" + providerId +
                '}';
    }
}
