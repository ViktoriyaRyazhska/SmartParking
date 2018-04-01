package com.smartparking.service.impl;

import com.smartparking.entity.Parking;
import com.smartparking.model.response.ParkingResponse;
import com.smartparking.repository.FavoriteRepository;
import com.smartparking.repository.ParkingRepository;
import com.smartparking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    public List<ParkingResponse> findAllNearbyResponse(Double latitude, Double longitude, Double radius) {
        Objects.requireNonNull(latitude, "latitude");
        Objects.requireNonNull(longitude, "longitude");
        Objects.requireNonNull(radius, "radius");
        if (radius < 0) {
            throw new IllegalArgumentException("Radius can`t be less then zero.");
        }
        return getRepository().findAllNearby(latitude, longitude, radius).stream().map(tuple -> {
            ParkingResponse response = tupleToParkingResponse(tuple);

            response.setFavoritesCount(favoriteService.getCountByClientId(response.getId()));
            response.setSpotsCount(spotService.countAllSpotsByParkingId(response.getId()));
            response.setAvailableSpotsCount(spotService.countAvailableSpotsByParkingId(response.getId()));

            providerService.findByParkingId(response.getId()).ifPresent(provider -> {
                response.setProviderId(provider.getId());
                response.setProviderName(provider.getName());
            });
            return response;
        }).collect(Collectors.toList());
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
        return response;
    }
}
