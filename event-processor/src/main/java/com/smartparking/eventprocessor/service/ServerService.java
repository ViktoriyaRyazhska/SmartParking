package com.smartparking.eventprocessor.service;

import java.io.IOException;
import java.util.Map;

public interface ServerService {

    void authenticate() throws IOException;

    void authenticateIfNeeded() throws IOException;

    boolean isAuthenticated();

    Map<Long, String> getParkingTokens() throws IOException;
}
