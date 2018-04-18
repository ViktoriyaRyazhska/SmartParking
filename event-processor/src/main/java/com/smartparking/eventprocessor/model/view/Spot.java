package com.smartparking.eventprocessor.model.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Spot {
    private Long id;
    private Parking parking;
    private Long spotNumber;
}
