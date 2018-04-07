package com.smartparking.model.response;

import lombok.Data;

@Data
public class ParkingTokenResponse {
    private Long parkingId;
    private String token;
}
