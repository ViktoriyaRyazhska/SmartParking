package com.smartparking.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "favorite")
@Data
@EqualsAndHashCode(callSuper = true)
public class Favorite extends AbstractIdentifiableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Parking parking;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
}
