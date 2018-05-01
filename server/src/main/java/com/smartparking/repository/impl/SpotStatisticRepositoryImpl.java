package com.smartparking.repository.impl;


import com.smartparking.controller.SpotController;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotStatisticRepositoryImpl.class);

    @Override
    public List<SpotStatisticResponse> getSpotStatistic(long id, long startDate, long endDate) {
         Date startTime = new Date(startDate);
         Date endTime = new Date(endDate);
        LOGGER.info("===========startTime=============="+startTime.toString());
        LOGGER.info("===========endTime=============="+endTime.toString());

        String sql = "CALL spotStatistic(?,?,?)";
        List<SpotStatisticResponse> result = new ArrayList<>();
        jdbcTemplate.query(sql,new Object[] { id, startTime, endTime },(rs, rowNum) ->
                new SpotStatisticResponse(rs.getLong("spot_number"), rs.getDouble("NumberOfhours"),
                        rs.getLong("NumberOfEvent"))
        ).forEach(response->result.add(response));

        return result;
    }


}
