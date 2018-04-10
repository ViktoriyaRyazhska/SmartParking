package com.smartparking.repository.impl;


import com.smartparking.controller.SpotController;
import com.smartparking.entity.Parking;
import com.smartparking.entity.Spot;
import com.smartparking.repository.ParkingRepository;
import com.smartparking.repository.SpotStatisticRepository;
import com.smartparking.service.ParkingService;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class SpotStatisticRepositoryImpl implements SpotStatisticRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotController.class);

    @Override
    public Map<Long, Double> getSpotStatistic(long id,long startDate, long endDate)
    {
       Date startTime = new Date(startDate);
        Date endTime = new Date(endDate);
                String sql = "SELECT  spot.id  \n" +
                ",(sum(CASE WHEN event.eventType = 1 THEN timestamp ELSE 0 END) \n" +
                "-\n" +
                "sum(CASE WHEN event.eventType = 0 THEN timestamp ELSE 0 END))/10000\n" +
                "AS NumberOfhours, \n" +
                  "COUNT(case event_marker when '0' then 1 else null end) \n" +
                  "as NumberOfEvent\n" +
                  "  FROM Spot spot \n" +
                "INNER JOIN Event event ON\n" +
                " event.spot=spot and parking_id = :id \n" +
                  " AND timestamp BETWEEN :startTime AND :endTime \n"+
                " GROUP BY spot.id ORDER BY NumberOfEvent DESC\n";
        Map<Long, Double> result = new HashMap<>();
        TypedQuery<Object[]> query = entityManager.createQuery(sql, Object[].class).setParameter("id",id)
                .setParameter("startTime",startTime).setParameter("endTime",endTime);
        for (Object[] objects : query.getResultList()) {
            result.put((Long) objects[0], (Double) objects[1]);
        }
        return result;
    }




}
