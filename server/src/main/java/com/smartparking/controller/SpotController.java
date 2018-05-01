package com.smartparking.controller;

import com.smartparking.entity.Client;
import com.smartparking.entity.Role;
import com.smartparking.entity.Spot;
import com.smartparking.model.request.SpotRequest;
import com.smartparking.model.request.SpotSearchCriterias;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


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
        allSpots.stream().forEach(spot -> {
            SpotStatusResponse spotStatusResponse = new SpotStatusResponse();
            spotStatusResponse.setId(spot.getSpotNumber());
            spotStatusResponse.setIsFree(freeSpots.contains(spot));
            spotStatusResponse.setHasCharger(spot.getHasCharger());
            spotStatusResponse.setIsInvalid(spot.getIsInvalid());
            spotStatusResponse.setSpotNumber(spot.getSpotNumber());
            if (spot.getIsBlocked()) {
                return;
            }
            spotStatusResponseList.add(spotStatusResponse);
        });
        spotStatusResponseList.sort(Comparator.comparing(SpotStatusResponse::getSpotNumber));
        return spotStatusResponseList;
    }

    @RequestMapping("parkingdetail/{id}/freespots")
    List<SpotStatusResponse> findAvailableSpotsDto(@PathVariable Long id) {
        List<Spot> freeSpots = spotService.findAllAvailableSpotsByParkingId(id);
        List<SpotStatusResponse> spotStatusResponseList = new ArrayList<>();
        freeSpots.stream().forEach(spot -> {
            SpotStatusResponse spotStatusResponse = new SpotStatusResponse();
            spotStatusResponse.setId(spot.getSpotNumber());
            spotStatusResponse.setIsFree(true);
            spotStatusResponse.setHasCharger(spot.getHasCharger());
            spotStatusResponse.setIsInvalid(spot.getIsInvalid());
            spotStatusResponse.setSpotNumber(spot.getSpotNumber());
            if (spot.getIsBlocked()) {
                return;
            }
            spotStatusResponseList.add(spotStatusResponse);
        });
        spotStatusResponseList.sort(Comparator.comparing(SpotStatusResponse::getSpotNumber));
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
    @PreAuthorize("@spotController.isValidNewSpot(#spotRequest.toSpot())")
    public ResponseEntity<?> save(@P("spotRequest") @RequestBody SpotRequest spotRequest) {
        if (spotRequest.getId() == null) {
            return createNewSpot(spotRequest.toSpot());
        } else {
            return editSpot(spotRequest.toSpot());
        }
    }

    @PostMapping("/manager-configuration/spot/delete")
    @PreAuthorize("hasAuthority('SUPERUSER') or @spotController.getCurrentUser().getProvider().getParkings().contains(#spotRequest.toSpot().parking)")
    public ResponseEntity<?> delete(@P("spotRequest") @RequestBody SpotRequest spotRequest) {
        Spot spot = spotRequest.toSpot();
        spotService.delete(spot);
        spotEventPublisher.publishDelete(spot);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/manager-configuration/spotsforparking/{parkingId}")
    @PreAuthorize("hasAuthority('SUPERUSER') or @spotController.getCurrentUser().getProvider().getParkings().contains(@parkingServiceImpl.findById(#parkingId).get())")
    public ResponseEntity<List<SpotStatusResponse>> spots(@P("parkingId") @PathVariable Long parkingId) {
        return new ResponseEntity<>(spotService.findAllSpotsByParkingIdResponse(parkingId), HttpStatus.OK);
    }

    @PostMapping("/manager-configuration/spotsforparking/{parkingId}/criterias")
    @PreAuthorize("hasAuthority('SUPERUSER') or @spotController.getCurrentUser().getProvider().getParkings().contains(@parkingServiceImpl.findById(#parkingId).get())")
    public ResponseEntity<List<SpotStatusResponse>> findSpots(@PathVariable Long parkingId, @RequestBody SpotSearchCriterias spotSearchCriterias) {
        return new ResponseEntity<>(spotService.findSpotsByCriterias(parkingId, spotSearchCriterias), HttpStatus.OK);
    }

    public ResponseEntity<?> createNewSpot(Spot spot) {
        if (spotService.findFirstBySpotNumberAndParking(spot.getSpotNumber(), spot.getParking()) == null) {
            spotService.save(spot);
            spotEventPublisher.publishSave(spot, 0);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Such number already exists", HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<?> editSpot(Spot spot) {
        if (spotService.findFirstBySpotNumberAndParking(spot.getSpotNumber(), spot.getParking()) == null ||
                spotService.findFirstBySpotNumberAndParking(spot.getSpotNumber(), spot.getParking()).getId() == spot.getId()) {
            spotService.save(spot);
            spotEventPublisher.publishSave(spot, spot.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Such number already exists", HttpStatus.BAD_REQUEST);
        }
    }

    public Client getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return clientService.findOne(email);
    }

    public Boolean isValidNewSpot(Spot spot) {
        Client client = getCurrentUser();
        return spot.getSpotNumber() != null && spot.getSpotNumber() < maxSpotNumber &&
                (client.getProvider().getParkings().contains(spot.getParking()) || client.getRole() == Role.SUPERUSER);
    }
}
