package com.smartparking.eventprocessor.service;

import com.smartparking.eventprocessor.model.view.UnverifiedEvent;
import com.smartparking.eventprocessor.model.view.VerifiedEvent;

import java.util.Collection;

public interface EventBatchService {

    VerifiedEvent pollVerified();

    UnverifiedEvent pollUnverified();

    void pushVerified(VerifiedEvent event);

    void pushUnverified(UnverifiedEvent event);

    void sendVerified(Collection<? extends VerifiedEvent> events);

    void sendUnverified(Collection<? extends UnverifiedEvent> events);
}
