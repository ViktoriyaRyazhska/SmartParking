package com.smartparking.controller;


import com.smartparking.controller.exception.BadRequestException;
import com.smartparking.entity.Client;
import com.smartparking.entity.Parking;
import com.smartparking.entity.Role;
import com.smartparking.model.request.ParkingNearbyRequest;
import com.smartparking.model.request.ParkingRequest;
import com.smartparking.model.request.ParkingSearchCriterias;
import com.smartparking.model.response.ParkingDetailResponse;
import com.smartparking.model.response.ParkingResponse;
import com.smartparking.model.response.ParkingWithSpotsResponse;
import com.smartparking.publisher.ParkingEventPublisher;
import com.smartparking.service.ClientService;
import com.smartparking.service.ParkingService;
import com.smartparking.service.ProviderService;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingController {

    @Autowired
    ClientService clientService;

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private SpotService spotService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private ParkingEventPublisher parkingEventPublisher;

    @GetMapping("parkings-nearby")
    public List<ParkingResponse> parkingsNearby(@ModelAttribute ParkingNearbyRequest request) {
        if (request.getRadius() < 0) {
            throw new BadRequestException("Radius must be positive or zero.");
        }
        return parkingService.findAllNearbyResponse(request);
    }

    @GetMapping("unique-cities")
    public ResponseEntity<List<String>> findUniqueCities() {
        List<String> cities = parkingService.findDistinctParkingCity();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("parkingdetail/{id}")
    public ParkingDetailResponse findParkingDetailResponseById(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Parking parking = parkingService.findById(id).orElse(null);
        ParkingDetailResponse parkingDetailResponse = ParkingDetailResponse.of(parking);
        if (auth == null) {
            parkingDetailResponse.setIsFavorite(false);
        } else {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            parkingDetailResponse.setIsFavorite(
                    parkingService.isFavorite(email, id));
            if (parkingDetailResponse.getIsFavorite()) {
                parkingDetailResponse.setFavoriteName(
                        parkingService.findFavoriteNameByEmailAndParkingId(email, id));
            }
        }
        parkingDetailResponse.setSpotsCount(
                spotService.countAllSpotsByParkingId(id)
        );
        parkingDetailResponse.setAvailableSpotsCount(
                spotService.countAvailableSpotsByParkingId(id)
        );
        return parkingDetailResponse;

    }

    @GetMapping("manager-configuration/parking/{id}")
    public ResponseEntity<?> configure(@PathVariable Long id) {
        return parkingService.findByIdResponse(id)
                .map(parking -> new ResponseEntity<Object>(parking, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<Object>("Incorrect parking id", HttpStatus.BAD_REQUEST));
    }

    @GetMapping("manager-configuration/parkings")
    public ResponseEntity<List<ParkingResponse>> parkings() {
        Client client = getCurrentUser();
        if (client.getRole() == Role.PROVIDER_MANAGER) {
            return new ResponseEntity<>(parkingService.findAllByProviderIdResponse(client.getProvider().getId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(parkingService.findAllResponse(), HttpStatus.OK);
        }
    }

    @PostMapping("/manager-configuration/parkings/criterias")
    public ResponseEntity<List<ParkingResponse>> findParkings(@RequestBody ParkingSearchCriterias parkingSearchCriterias) {
        Client client = getCurrentUser();
        return new ResponseEntity<>(parkingService.findParkingsByCriterias(client, parkingSearchCriterias), HttpStatus.OK);
    }

    @PostMapping("/manager-configuration/parking/save")
    public ResponseEntity<?> save(@RequestBody ParkingRequest parkingRequest) {
        Client client = getCurrentUser();
        if (parkingRequest.getProviderId() == null) {
            parkingRequest.setProviderId(client.getProvider().getId());
        }
        Parking parking = parkingRequest.toParking();
        parking.setProvider(providerService.getOne(parkingRequest.getProviderId()));
        if (client.getRole() == Role.SUPERUSER || parking.getProvider().getEmployees().contains(client)) {
            long parkingId = 0;
            if (parking.getId() != null) {
                parkingId = parking.getId();
            }
            parkingService.save(parking);
            parkingEventPublisher.publishSave(parking, parkingId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/manager-configuration/parking/delete")
    public ResponseEntity<?> delete(@RequestBody ParkingRequest parkingRequest) {
        Client client = getCurrentUser();
        Parking parking = parkingRequest.toParking();
        if (client.getRole() == Role.SUPERUSER || client.getProvider().getParkings().contains(parking)) {
            parkingService.delete(parking);
            parkingEventPublisher.publishDelete(parking);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/parkings-with-spots")
    public List<ParkingWithSpotsResponse> parkingWithSpots() {
        return parkingService.findAllWithSpotsResponse();
    }

    private Client getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return clientService.findOne(email);
    }
}