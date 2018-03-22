package com.smartparking.model.request;

public class ProviderStatisticRequest {
    private String activeAmount;
    private String nonactiveAmount;
    private String allAmount;

    public String getActiveAmount() {
        return activeAmount;
    }

    public void setActiveAmount(String activeAmount) {
        this.activeAmount = activeAmount;
    }

    public String getNonactiveAmount() {
        return nonactiveAmount;
    }

    public void setNonactiveAmount(String nonactiveAmount) {
        this.nonactiveAmount = nonactiveAmount;
    }

    public String getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(String allAmount) {
        this.allAmount = allAmount;
    }
}
