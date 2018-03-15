package com.smartparking.repository;

import com.smartparking.entity.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends Repository<Event, Long> {

    @Query("SELECT event " +
            "FROM Event event " +
            "WHERE event.departureTime IS NULL AND " +
            "event.spot.id = :spotId")
    Event findBySpotId(@Param("spotId") Long spotId);
}