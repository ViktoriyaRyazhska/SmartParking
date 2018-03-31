package com.smartparking.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "spot")
@Data
@EqualsAndHashCode(callSuper = true)
public class Spot extends AbstractIdentifiableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Parking parking;

    @OneToMany(mappedBy = "spot")
    private List<Event> events;
}
