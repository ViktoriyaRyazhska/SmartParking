package com.smartparking.repository;

import com.smartparking.entity.PasswordConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordConfirmationRepository extends JpaRepository<PasswordConfirmation, Long> {

    PasswordConfirmation findByUuid(String uuid);
}
