package com.smartparking.controller;

import com.smartparking.entity.Spot;
import com.smartparking.model.response.SpotStatisticResponse;
import com.smartparking.model.response.SpotStatusResponse;
import com.smartparking.service.SpotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class SpotController {

    private final SpotService spotService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotController.class);



    @Autowired
    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    @RequestMapping("parkingdetail/{id}/spots")
    List<SpotStatusResponse> findAllSpotsDto(@PathVariable Long id) {
        List<Spot> allSpots = spotService.findAllSpotsByParkingId(id);
        List<Spot> freeSpots = spotService.findAllAvailableSpotsByParkingId(id);
        List<SpotStatusResponse> spotStatusResponseList = new ArrayList<>();
        for (Spot spot : allSpots) {
            SpotStatusResponse spotStatusResponse = new SpotStatusResponse();
            spotStatusResponse.setId(spot.getId());
            spotStatusResponse.setIsFree(freeSpots.contains(spot));
            spotStatusResponseList.add(spotStatusResponse);
        }
        return spotStatusResponseList;
    }

    @RequestMapping("parkingdetail/{id}/freespots")
    List<SpotStatusResponse> findAvailableSpotsDto(@PathVariable Long id) {
        List<Spot> freeSpots = spotService.findAllAvailableSpotsByParkingId(id);
        List<SpotStatusResponse> spotStatusResponseList = new ArrayList<>();
        for (Spot spot : freeSpots) {
            SpotStatusResponse spotStatusResponse = new SpotStatusResponse();
            spotStatusResponse.setId(spot.getId());
            spotStatusResponse.setIsFree(true);
            spotStatusResponseList.add(spotStatusResponse);
        }
        return spotStatusResponseList;
    }

    @RequestMapping("spotstatistic/{id}")
    List<SpotStatisticResponse> getSpotStatistic (@PathVariable Long id/*, @RequestBody DateRangeRequest request*/) {


        List<SpotStatisticResponse> spotStatisticResponseList = new ArrayList<>();
       /* if(request ==  null) {
            LOGGER.info("==================================request  is null  method is working!!!!=================================");*/

            Map<Long, Double> spotStatistic = spotService.getSpotStatistic(id);
            for (Map.Entry<Long, Double> entry : spotStatistic.entrySet()) {
                Long key = entry.getKey();
                Double value = entry.getValue();
                SpotStatisticResponse spotStatisticResponse = new SpotStatisticResponse();
                spotStatisticResponse.setId(key);
                spotStatisticResponse.setNumberOfHours(value);
                spotStatisticResponseList.add(spotStatisticResponse);
            }

          /*  }
        } else
            LOGGER.info("=================================="+request.getBeginDate()+"=================================");
        LOGGER.info("=================================="+request.getEndDate()+"=================================");*/
        LOGGER.info(spotStatisticResponseList.toString());
        return spotStatisticResponseList;
    }


    /*@RequestMapping("spotstatistic/byDate/{id}")
    List<SpotStatisticResponse> getSpotStatisticByDateRange (@PathVariable Long id,@RequestBody ClientRequest clientRequest) {
        List<SpotStatisticResponse> spotStatisticResponseList = new ArrayList<>();

        /*Map<Long, Double > spotStatistic = spotService.getSpotStatistic(id);
        for(Map.Entry<Long, Double> entry : spotStatistic.entrySet()) {
            Long key = entry.getKey();
            Double value = entry.getValue();
            SpotStatisticResponse spotStatisticResponse = new SpotStatisticResponse();
            spotStatisticResponse.setId(key);
            spotStatisticResponse.setNumberOfHours(value);
            spotStatisticResponseList.add(spotStatisticResponse);

        }

        return spotStatisticResponseList;
    }

*/

}
