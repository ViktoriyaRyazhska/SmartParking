package com.smartparking.eventprocessor.service;

import com.smartparking.eventprocessor.controller.exception.FailureException;
import com.smartparking.eventprocessor.model.view.Spot;

import java.io.IOException;
import java.util.Map;

public interface ServerService {

    void authenticate() throws IOException;

    void authenticateIfNeeded() throws IOException;

    boolean isAuthenticated();

    Map<Long, Spot> getSpots() throws IOException, FailureException;
}
