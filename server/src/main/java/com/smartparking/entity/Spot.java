package com.smartparking.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "spot")
@Getter
@Setter
@NoArgsConstructor
public class Spot extends AbstractIdentifiableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Parking parking;

    @OneToMany(mappedBy = "spot")
    private List<Event> events;

    @NotNull
    @Column(name = "spot_number", nullable = false)
    private Long spotNumber;

    public Spot(Long id) {
        super(id);
    }
}
