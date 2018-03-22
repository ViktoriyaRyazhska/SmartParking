package com.smartparking.repository;

import com.smartparking.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    List<Parking> findAllByProviderId(Long id);

    @Query(value = "SELECT * FROM parking WHERE LOCATION_DISTANCE(" +
            ":latitude, :longitude, parking.latitude, parking.longitude) <= :radius", nativeQuery = true)
    List<Parking> findAllNearby(@Param(value = "latitude") Double latitude,
                                @Param(value = "longitude") Double longitude,
                                @Param(value = "radius") Double radius);
}
