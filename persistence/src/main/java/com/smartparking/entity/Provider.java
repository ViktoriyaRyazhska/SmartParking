package com.smartparking.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "provider")
public class Provider extends AbstractIdentifiableEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.REMOVE)
    private List<Parking> parkings;

    @OneToMany(mappedBy = "provider")
    private List<Client> employees;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Address legalAddress;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Parking> getParkings() {
        return parkings;
    }

    public void setParkings(List<Parking> parkings) {
        this.parkings = parkings;
    }

    public List<Client> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Client> employees) {
        this.employees = employees;
    }

    public Address getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(Address legalAddress) {
        this.legalAddress = legalAddress;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
