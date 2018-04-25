package com.smartparking.controller;

import com.smartparking.entity.Client;
import com.smartparking.entity.Role;
import com.smartparking.entity.Spot;
import com.smartparking.model.request.SpotRequest;
import com.smartparking.model.response.SpotStatisticResponse;
import com.smartparking.model.response.SpotStatusResponse;
import com.smartparking.publisher.SpotEventPublisher;
import com.smartparking.service.ClientService;
import com.smartparking.service.ParkingService;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class SpotController {

    private final SpotService spotService;
    @Autowired
    ClientService clientService;

    @Autowired
    ParkingService parkingService;

    @Autowired
    SpotEventPublisher spotEventPublisher;
    @Value("${validation.spotnumber.max}")
    private Long maxSpotNumber;


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
            spotStatusResponse.setId(spot.getSpotNumber());
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
            spotStatusResponse.setId(spot.getSpotNumber());
            spotStatusResponse.setIsFree(true);
            spotStatusResponseList.add(spotStatusResponse);
        }
        return spotStatusResponseList;
    }

    @RequestMapping("parkingdetail/{id}/spotstatistic")
    public ResponseEntity<List<SpotStatisticResponse>> getSpotStatistic(
            @PathVariable Long id,
            @RequestParam("start_time") String startTime,
            @RequestParam("end_time") String endTime) {


        List<SpotStatisticResponse> spotStatisticResponseList =
                spotService.getSpotStatistic(id, Long.parseLong(startTime), Long.parseLong(endTime));
        return new ResponseEntity<>(spotStatisticResponseList, HttpStatus.OK);
    }

    @PostMapping("/manager-configuration/spot/save")
    public ResponseEntity<?> save(@RequestBody SpotRequest spotRequest) {
        Client client = getCurrentUser();
        Spot spot = spotRequest.toSpot();
        spot.setParking(parkingService.getOne(spotRequest.getParkingId()));
        if (isValidNewSpot(spot, client)) {
            Optional<Spot> optional = spot.getParking().getSpots().stream().filter(spot1 -> spot1.getSpotNumber().equals(spot.getSpotNumber())).findFirst();
            long spotId = 0;
            if (spot.getId() != null) {
                spotId = spot.getId();
                if (optional.isPresent()) {
                    if (optional.get().getId() != spotId) {
                        return new ResponseEntity<>("This spot number is exist", HttpStatus.BAD_REQUEST);
                    }
                }
            } else {
                if (optional.isPresent()) {
                    return new ResponseEntity<>("This spot number is exist", HttpStatus.BAD_REQUEST);
                }
            }
            spotService.save(spot);
            spotEventPublisher.publishSave(spot, spotId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot save spot", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/manager-configuration/spot/delete")
    public ResponseEntity<?> delete(@RequestBody SpotRequest spotRequest) {
        Client client = getCurrentUser();
        Spot spot = spotRequest.toSpot();
        if (client.getProvider().getParkings().stream().filter(parking -> parking.getId().equals(spotRequest.getParkingId())).findFirst().isPresent() ||
                client.getRole() == Role.SUPERUSER) {
            spotService.delete(spot);
            spotEventPublisher.publishDelete(spot);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/manager-configuration/spotsforparking/{parkingId}")
    public ResponseEntity<List<SpotStatusResponse>> spots(@PathVariable Long parkingId) {
        Client client = getCurrentUser();
        if (client.getProvider().getParkings().stream().filter(parking -> parking.getId().equals(parkingId)).findFirst().isPresent() ||
                client.getRole() == Role.SUPERUSER) {
            return new ResponseEntity<>(spotService.findAllSpotsByParkingIdResponse(parkingId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private Client getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return clientService.findOne(email);
    }

    private Boolean isValidNewSpot(Spot spot, Client client) {
        return spot.getSpotNumber() != null && spot.getSpotNumber() < maxSpotNumber &&
                (client.getProvider().getParkings().contains(spot.getParking()) || client.getRole() == Role.SUPERUSER);
    }
}
