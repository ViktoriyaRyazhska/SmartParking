package com.smartparking.eventprocessor.service;

import com.smartparking.eventprocessor.controller.exception.FailureException;
import com.smartparking.eventprocessor.model.response.ParkingWithSpotsResponse;
import com.smartparking.eventprocessor.model.view.Event;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface ServerService {

    void authenticate() throws IOException;

    void authenticateIfNeeded() throws IOException;

    boolean isAuthenticated();

    List<ParkingWithSpotsResponse> getParkingsWithSpots() throws IOException, FailureException;

    void sendEvents(Collection<? extends Event> events) throws IOException;

    ServerStatus getServerStatus();

    default boolean isServerAvailable() {
        return getServerStatus() == ServerStatus.AVAILABLE;
    }

    enum ServerStatus {
        AVAILABLE, UNAVAILABLE
    }
}
