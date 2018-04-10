package com.smartparking.eventprocessor.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.smartparking.eventprocessor.config.properties.HttpClientProperties;
import com.smartparking.eventprocessor.model.request.LoginRequest;
import com.smartparking.eventprocessor.model.request.VerifiedEventRequest;
import com.smartparking.eventprocessor.model.response.AuthTokenResponse;
import com.smartparking.eventprocessor.model.response.ParkingWithSpotsResponse;
import com.smartparking.eventprocessor.model.view.Event;
import com.smartparking.eventprocessor.model.view.Parking;
import com.smartparking.eventprocessor.model.view.Spot;
import com.smartparking.eventprocessor.service.HttpClientService;
import com.smartparking.eventprocessor.service.ServerService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServerServiceImpl implements ServerService {

    @Autowired
    private HttpClientProperties httpClientProperties;

    @Autowired
    private HttpClientService httpClientService;

    private volatile String token;

    @Getter
    private volatile ServerStatus serverStatus = ServerStatus.UNAVAILABLE;

    @Override
    public synchronized void authenticate() throws IOException {
        try {
            LoginRequest request = new LoginRequest();
            request.setEmail(httpClientProperties.getEmail());
            request.setPassword(httpClientProperties.getPassword());
            AuthTokenResponse response = httpClientService.postAndReceiveBody(
                    "/auth/generate-token", request, null, new TypeReference<AuthTokenResponse>() {});
            token = response.getAccessToken();
            serverStatus = ServerStatus.AVAILABLE;
        } catch (IOException ex) {
            serverStatus = ServerStatus.UNAVAILABLE;
            throw ex;
        }
    }

    @Override
    public void authenticateIfNeeded() throws IOException {
        if (this.token == null) {
            synchronized (this) {
                if (this.token == null) {
                    authenticate();
                }
            }
        }
    }

    @Override
    public boolean isAuthenticated() {
        return token != null;
    }

    @Override
    public List<Spot> getSpots() throws IOException {
        try {
            authenticateIfNeeded();
            List<ParkingWithSpotsResponse> response = httpClientService.getAndReceiveBody(
                    "/parkings-with-spots", null, token, new TypeReference<List<ParkingWithSpotsResponse>>() {});
            serverStatus = ServerStatus.AVAILABLE;
            return response.stream().
                    flatMap(p -> {
                        Parking parking = new Parking(p.getId(), p.getToken());
                        return p.getSpots().stream().map(s -> new Spot(s.getId(), parking));
                    })
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            serverStatus = ServerStatus.UNAVAILABLE;
            throw ex;
        }
    }

    @Override
    public void sendEvents(Collection<? extends Event> events) throws IOException {
        try {
            authenticateIfNeeded();
            List<VerifiedEventRequest> requests = events.stream()
                    .map(VerifiedEventRequest::new)
                    .collect(Collectors.toList());
            httpClientService.postAndReceiveStatus("/events/save", requests, token);
            serverStatus = ServerStatus.AVAILABLE;
        } catch (IOException ex) {
            serverStatus = ServerStatus.UNAVAILABLE;
            throw ex;
        }
    }
}
