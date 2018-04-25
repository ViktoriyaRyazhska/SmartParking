package com.smartparking.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotStatusResponse {
    private Long id;
    private Boolean isFree;
    private Long spotNumber;
    private Long parkingId;
    private Boolean hasCharger;
    private Boolean isInvalid;
    private Boolean isBlocked;
}
