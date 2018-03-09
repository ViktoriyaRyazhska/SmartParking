package com.smartparking.entity.v2;

import com.smartparking.entity.Favorite;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "parking")
public class Parking extends AbstractEntityId {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Address address;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Min(0)
    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Provider provider;

    @OneToMany(mappedBy = "parking", cascade = CascadeType.REMOVE)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "parking", cascade = CascadeType.REMOVE)
    private List<Spot> spots;

}
