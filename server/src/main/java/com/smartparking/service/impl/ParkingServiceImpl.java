package com.smartparking.service.impl;

import com.smartparking.entity.Parking;
import com.smartparking.entity.Spot;
import com.smartparking.model.request.ParkingNearbyRequest;
import com.smartparking.model.request.ParkingSearchCriterias;
import com.smartparking.model.response.ParkingResponse;
import com.smartparking.model.response.ParkingWithSpotsResponse;
import com.smartparking.model.response.SpotResponse;
import com.smartparking.repository.FavoriteRepository;
import com.smartparking.repository.ParkingRepository;
import com.smartparking.service.*;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParkingServiceImpl extends AbstractService<Parking, Long, ParkingRepository> implements ParkingService {

    @Autowired
    private SpotService spotService;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private ProviderService providerService;

    protected ParkingServiceImpl(@Autowired ParkingRepository repository) {
        super(repository);
    }

    @Override
    public List<ParkingResponse> findAllNearbyResponse(@NonNull ParkingNearbyRequest request) {
        if (request.getRadius() < 0) {
            throw new IllegalArgumentException("Radius can`t be less then zero.");
        }
        return getRepository().findAllNearby(request.getLatitude(), request.getLongitude(), request.getRadius()).stream()
                .map(tuple -> {
                    ParkingResponse response = tupleToParkingResponse(tuple);

                    response.setFavoritesCount(favoriteService.getCountByClientId(response.getId()));
                    response.setSpotsCount(spotService.countAllSpotsByParkingId(response.getId()));
                    response.setAvailableSpotsCount(spotService.countAvailableSpotsByParkingId(response.getId()));

                    providerService.findByParkingId(response.getId()).ifPresent(provider -> {
                        response.setProviderId(provider.getId());
                        response.setProviderName(provider.getName());
                    });
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ParkingWithSpotsResponse> findAllWithSpotsResponse() {
        Map<Long, ParkingWithSpotsResponse> responses = new HashMap<>();
        ParkingWithSpotsResponse parkingResponse;
        for (Parking parking : getRepository().findAll()) {
            parkingResponse = new ParkingWithSpotsResponse();
            parkingResponse.setId(parking.getId());
            parkingResponse.setToken(parking.getToken());
            parkingResponse.setSpots(new ArrayList<>());
            responses.put(parking.getId(), parkingResponse);
        }

        for (Spot spot : spotService.findAll()) {
            Long parkingId = spot.getParking().getId();
            parkingResponse = responses.get(parkingId);
            parkingResponse.getSpots().add(new SpotResponse(spot.getId(), spot.getSpotNumber()));
        }
        return new ArrayList<>(responses.values());
    }

    @Override
    public Boolean isFavorite(String email, Long parkingId) {
        return favoriteRepository.findByClientEmailAndParkingId(email, parkingId)
                .isPresent();
    }

    @Override
    public String findFavoriteNameByEmailAndParkingId(String email, Long parkingId) {
        return favoriteRepository.findByClientEmailAndParkingId(email, parkingId).get();
    }

    @Override
    public List<String> findDistinctParkingCity() {
        return getRepository().findAllParkingCities();
    }

    @Override
    public List<ParkingResponse> findAllByProviderIdResponse(Long providerId) {
        Objects.requireNonNull(providerId, "clientId");
        return getRepository().findAllByProviderId(providerId).stream()
                .map(this::parkingToParkingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ParkingResponse> findByIdResponse(Long id) {
        return getRepository().findById(id).map(this::parkingToParkingResponse);
    }

    private ParkingResponse parkingToParkingResponse(Parking parking) {
        ParkingResponse response = new ParkingResponse();
        response.setId(parking.getId());
        response.setCity(parking.getCity());
        response.setStreet(parking.getStreet());
        response.setBuilding(parking.getBuilding());
        response.setLatitude(parking.getLatitude());
        response.setLongitude(parking.getLongitude());
        response.setPrice(parking.getPrice());
        response.setToken(parking.getToken());
        response.setProviderId(parking.getProvider().getId());
        response.setProviderName(parking.getProvider().getName());
        response.setFavoritesCount((long) parking.getFavorites().size());
        response.setSpotsCount((long) parking.getSpots().size());
        response.setAvailableSpotsCount(spotService.countAvailableSpotsByParkingId(parking.getId()));
        response.setHasCharger(parking.getHasCharger());
        response.setHasInvalid(parking.getHasInvalid());
        response.setIsCovered(parking.getIsCovered());
        return response;
    }

    private ParkingWithSpotsResponse tupleToParkingTokenResponse(Tuple tuple) {
        ParkingWithSpotsResponse response = new ParkingWithSpotsResponse();
        response.setId(tuple.get(0, Long.class));
        response.setToken(tuple.get(1, String.class));
        return response;
    }

    private ParkingResponse tupleToParkingResponse(Tuple tuple) {
        ParkingResponse response = new ParkingResponse();
        response.setId(tuple.get("id", BigInteger.class).longValue());
        response.setPrice(tuple.get("price", BigDecimal.class));
        response.setCity(tuple.get("city", String.class));
        response.setStreet(tuple.get("street", String.class));
        response.setToken(tuple.get("token", String.class));
        response.setBuilding(tuple.get("building", String.class));
        response.setLatitude(tuple.get("latitude", Double.class));
        response.setLongitude(tuple.get("longitude", Double.class));
        response.setDistance(tuple.get("distance", Double.class));
        response.setHasCharger(tuple.get("has_charger", Boolean.class));
        response.setHasInvalid(tuple.get("has_invalid", Boolean.class));
        response.setIsCovered(tuple.get("is_covered", Boolean.class));
        return response;
    }

    @Override
    public List<Parking> findParkingsByCity(String input) {
        return getRepository().findParkingsByCity(input);
    }

    @Override
    public List<String> findParkingStreetByAnyMatch(String city, String street) {
        return getRepository().findParkingStreetByAnyMatch(city, street);
    }

    @Override
    public List<String> findParkingCitiesByAnyMatch(String city) {
        return getRepository().findParkingCitiesByAnyMatch(city);
    }

    @Override
    public List<String> findAllParkingCities() {
        return getRepository().findAllParkingCities();
    }

    @Override
    public List<ParkingResponse> findParkingsByCriterias(Long providerId, ParkingSearchCriterias criterias) {
        List<ParkingResponse> filteredParkings = findAllByProviderIdResponse(providerId)
                .stream().filter(parkingResponse -> {
                    if ((parkingResponse.getCity() + " " + parkingResponse.getStreet() + " " + parkingResponse.getBuilding()).
                            toLowerCase().contains(criterias.getSearch().toLowerCase())) {
                        if (criterias.getAll()) {
                            return true;
                        }
                        if (parkingResponse.getHasCharger() == criterias.getHasCharger() && criterias.getHasCharger()) {
                            return true;
                        }
                        if (parkingResponse.getIsCovered() == criterias.getIsCovered() && criterias.getIsCovered()) {
                            return true;
                        }
                    }
                    return false;
                }).collect(Collectors.toList());
        return filteredParkings;
    }
}
