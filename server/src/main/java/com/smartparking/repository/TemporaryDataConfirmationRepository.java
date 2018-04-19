package com.smartparking.repository;

import com.smartparking.entity.TemporaryDataConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporaryDataConfirmationRepository extends JpaRepository<TemporaryDataConfirmation, Long> {

    TemporaryDataConfirmation findByUuid(String uuid);
}
