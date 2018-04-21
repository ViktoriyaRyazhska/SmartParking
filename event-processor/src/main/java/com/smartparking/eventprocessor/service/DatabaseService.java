package com.smartparking.eventprocessor.service;

import com.smartparking.eventprocessor.model.view.Event;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.util.Collection;

public interface DatabaseService {
    void saveEvents(Collection<Event> events) throws DataAccessException, SQLException;
}
