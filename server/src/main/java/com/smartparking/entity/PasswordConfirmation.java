package com.smartparking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "password_confirmation")
@Getter
@Setter
public class PasswordConfirmation extends AbstractIdentifiableEntity{

    @NotNull
    private String uuid;

    @NotNull
    @Column(name = "new_password")
    private String newPassword;

    @NotNull
    @Column(name = "time_stamp")
    private Instant timeStamp;

}
