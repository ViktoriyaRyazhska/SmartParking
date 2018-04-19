package com.smartparking.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotNumberChangeEvent {
    private Long spotId;
    private Long parkingId;
    private Long spotNumber;
}
