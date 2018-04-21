package com.smartparking.eventprocessor.service.impl;

import com.smartparking.eventprocessor.model.view.Event;
import com.smartparking.eventprocessor.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveEvents(Collection<Event> events) throws DataAccessException, SQLException {
        DataSource ds = jdbcTemplate.getDataSource();
        try (Connection connection = ds.getConnection()) {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO event (spot_id, event_marker, timestamp) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            final int batchSize = 1000;
            int count = 0;
            for (Event event : events) {
                ps.setLong(1, event.getSpotId());
                ps.setLong(2, event.getEventType().getId());
                ps.setTimestamp(3, new Timestamp(event.getTimestamp().toEpochMilli()));
                ps.addBatch();

                if (++count % batchSize == 0) {
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }
            ps.executeBatch(); // insert remaining records
            ps.clearBatch();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            throw e;
        }
    }
}
