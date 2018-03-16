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
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @NotNull
    @Column(name = "occasion", nullable = false)
    private Occasion occasion;


    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Occasion getOccasion() {
        return occasion;
    }

    public void setOccasion(Occasion occasion) {
        this.occasion = occasion;
    }

    public void setCurrentEventTime() {
        this.timestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC);
    }
}
