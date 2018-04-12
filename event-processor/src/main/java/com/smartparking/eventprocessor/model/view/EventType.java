package com.smartparking.eventprocessor.model.view;

public enum EventType {
    ARRIVE(0), DEPARTURE(1), BLOCK(2);

    private final int id;

    EventType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
