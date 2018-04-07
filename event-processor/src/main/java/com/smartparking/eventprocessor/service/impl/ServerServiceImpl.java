package com.smartparking.eventprocessor.service.impl;

import com.smartparking.eventprocessor.config.properties.HttpClientProperties;
import com.smartparking.eventprocessor.controller.exception.FailureException;
import com.smartparking.eventprocessor.model.request.LoginRequest;
import com.smartparking.eventprocessor.model.response.AuthTokenResponse;
import com.smartparking.eventprocessor.model.response.ParkingWithSpotsResponse;
import com.smartparking.eventprocessor.model.view.Event;
import com.smartparking.eventprocessor.model.view.Parking;
import com.smartparking.eventprocessor.model.view.Spot;
import com.smartparking.eventprocessor.service.HttpClientService;
import com.smartparking.eventprocessor.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServerServiceImpl implements ServerService {

    @Autowired
    private HttpClientProperties httpClientProperties;

    @Autowired
    private HttpClientService httpClientService;

    private volatile String token;

    @Override
    public void authenticate() throws IOException, FailureException {
        LoginRequest request = new LoginRequest();
        request.setEmail(httpClientProperties.getEmail());
        request.setPassword(httpClientProperties.getPassword());
        AuthTokenResponse response =
                httpClientService.post("/auth/generate-token", request, null, AuthTokenResponse.class);
        synchronized (this) {
            token = response.getToken();
        }
    }

    @Override
    public void authenticateIfNeeded() throws IOException {
        String token = this.token;
        if (token == null) {
            authenticate();
        }
    }

    @Override
    public boolean isAuthenticated() {
        return token != null;
    }

    @Override
    public Map<Long, Spot> getSpots() throws IOException, FailureException {
        authenticateIfNeeded();
        List<ParkingWithSpotsResponse> response =
                httpClientService.getList("/parkings-with-spots", null, token, ParkingWithSpotsResponse.class);
        return response.stream().flatMap(p -> {
            Parking parking = new Parking(p.getId(), p.getToken());
            return p.getSpots().stream().map(s -> new Spot(s.getId(), parking));
        }).collect(Collectors.toMap(Spot::getId, s -> s));
    }

    @Override
    public void sendEvents(Collection<? extends Event> events) throws IOException, FailureException {
        authenticateIfNeeded();
        List<ParkingWithSpotsResponse> response =
                httpClientService.getList("/events/save", null, token, ParkingWithSpotsResponse.class);
        /*return response.stream().flatMap(p -> {
            Parking parking = new Parking(p.getId(), p.getToken());
            return p.getSpots().stream().map(s -> new Spot(s.getId(), parking));
        }).collect(Collectors.toMap(Spot::getId, s -> s));*/
    }
}
