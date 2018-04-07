package com.smartparking.eventprocessor.service.impl;

import com.smartparking.eventprocessor.config.properties.HttpClientProperties;
import com.smartparking.eventprocessor.model.request.LoginRequest;
import com.smartparking.eventprocessor.model.response.AuthTokenResponse;
import com.smartparking.eventprocessor.model.response.ParkingTokenResponse;
import com.smartparking.eventprocessor.service.HttpClientService;
import com.smartparking.eventprocessor.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public void authenticate() throws IOException {
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
    public Map<Long, String> getParkingTokens() throws IOException {
        authenticateIfNeeded();
        List<ParkingTokenResponse> response =
                httpClientService.getList("/parking-tokens", null, token, ParkingTokenResponse.class);
        return response.stream().collect(Collectors.toMap(
                ParkingTokenResponse::getParkingId, ParkingTokenResponse::getToken));
    }
}
