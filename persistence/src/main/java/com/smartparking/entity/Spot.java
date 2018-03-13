package com.smartparking.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "spot")
public class Spot extends AbstractIdentifiableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Parking parking;

    @OneToMany(mappedBy = "spot")
    private List<Event> events;

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
