package com.smartparking.repository.impl;


import com.smartparking.entity.Client;
import com.smartparking.entity.Spot;
import com.smartparking.model.response.SpotStatisticResponse;
import com.smartparking.repository.SpotStatisticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.*;

@Repository
public class SpotStatisticRepositoryImpl implements SpotStatisticRepository {

     @Autowired
    JdbcTemplate jdbcTemplate;



    @Override
    public List<SpotStatisticResponse> getSpotStatistic(long id, long startDate, long endDate) {
         Date startTime = new Date(startDate);
         Date endTime = new Date(endDate);
        String sql = "SELECT  spot.id  \n" +
                ",(sum(CASE WHEN event_marker = 1 THEN timestamp ELSE 0 END) \n" +
                "-\n" +
                "sum(CASE WHEN event_marker = 0 THEN timestamp ELSE 0 END))/10000\n" +
                "AS NumberOfhours, \n" +
                "COUNT(case event_marker when '0' then 1 else null end) \n" +
                "as NumberOfEvent\n" +
                "  FROM spot \n" +
                "INNER JOIN Event event ON\n" +
                " event.spot_id=spot.id and parking_id = ? \n" +
                " AND timestamp BETWEEN ? AND ? \n" +
                " GROUP BY spot.id ORDER BY NumberOfEvent DESC\n";

        List<SpotStatisticResponse> result = new ArrayList<>();
        jdbcTemplate.query(sql,new Object[] { id, startTime, endTime },(rs, rowNum) ->
                new SpotStatisticResponse(rs.getLong("id"), rs.getDouble("NumberOfhours"),
                        rs.getLong("NumberOfEvent"))
        ).forEach(response->result.add(response));

        return result;
    }


}
