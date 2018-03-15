package com.smartparking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "event")
public class Event extends AbstractIdentifiableEntity {

    @NotNull
    @ManyToOne(optional = false)
    private Spot spot;

    @NotNull
    @Column(name = "arrival_time", nullable = false)
    private Instant arrivalTime;

    @Column(name = "departure_time")
    private Instant departureTime;

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Instant getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Instant departureTime) {
        this.departureTime = departureTime;
    }

    public void setCurrentArrivalTime() {
        this.arrivalTime = LocalDateTime.now().toInstant(ZoneOffset.UTC);
    }

    public void setCurrentDepartureTime() {
        this.departureTime = LocalDateTime.now().toInstant(ZoneOffset.UTC);
    }
}
