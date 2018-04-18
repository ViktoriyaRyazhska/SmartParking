package com.smartparking.eventprocessor.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotAddEvent {
    private Long spotId;
    private Long parkingId;
    private Long spotNumber;
}
