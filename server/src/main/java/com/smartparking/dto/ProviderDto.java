package com.smartparking.dto;

import com.smartparking.entity.Address;
import com.smartparking.entity.Client;
import com.smartparking.entity.Parking;
import com.smartparking.entity.Provider;

import java.util.List;

public class ProviderDto {
    private Long id;
    private String name;
    private List<ParkingDto> parkingsDto;
    private List<ClientDto> employeesDto;
    private AddressDto legalAddressDto;
    private Boolean active;

    public static ProviderDto builder(){
        ProviderDto providerDto = new ProviderDto();
        return providerDto;
    }

    public Long getId() {
        return id;
    }

    public ProviderDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProviderDto setName(String name) {
        this.name = name;
        return this;
    }

    public List<ParkingDto> getParkingsDto() {
        return parkingsDto;
    }

    public ProviderDto setParkingsDto(List<ParkingDto> parkingsDto) {
        this.parkingsDto = parkingsDto;
        return this;
    }

    public List<ClientDto> getEmployeesDto() {
        return employeesDto;
    }

    public ProviderDto setEmployeesDto(List<ClientDto> employeesDto) {
        this.employeesDto = employeesDto;
        return this;
    }

    public AddressDto getLegalAddressDto() {
        return legalAddressDto;
    }

    public ProviderDto setLegalAddressDto(AddressDto legalAddressDto) {
        this.legalAddressDto = legalAddressDto;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public ProviderDto setActive(Boolean active) {
        this.active = active;
        return this;
    }
}