package com.smartparking.eventprocessor.service.impl;

import com.smartparking.eventprocessor.model.view.Event;
import com.smartparking.eventprocessor.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collection;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void saveEvents(Collection<Event> events) throws DataAccessException {
        String sql = "INSERT INTO event (spot_id, event_marker, timestamp) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, events, events.size(), (statement, event) -> {
            statement.setLong(1, event.getSpotId());
            statement.setLong(2, event.getEventType().getId());
            statement.setTimestamp(3, new Timestamp(event.getTimestamp().toEpochMilli()));
        });
    }
}
