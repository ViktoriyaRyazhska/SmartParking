package com.smartparking.model.request;

import lombok.Data;

@Data
public class SpotSearchCriterias {
    private String search;
    private Boolean all;
    private Boolean hasCharger;
    private Boolean isInvalid;
    private Boolean isBlocked;

}
