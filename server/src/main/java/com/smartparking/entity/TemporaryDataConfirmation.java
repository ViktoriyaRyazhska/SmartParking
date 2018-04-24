package com.smartparking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "temporary_data_confirmation")
@Getter
@Setter
public class TemporaryDataConfirmation extends AbstractIdentifiableEntity{

    @NotNull
    private String uuid;

    @Column(name = "new_password")
    private String newPassword;

    @NotNull
    @Column(name = "time_stamp")
    private Instant timeStamp;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "confirmation_type")
    private ConfirmationType confirmationType;

}
