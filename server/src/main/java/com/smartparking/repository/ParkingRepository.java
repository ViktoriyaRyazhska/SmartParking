package com.smartparking.repository;

import com.smartparking.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking,Long> {
}
