package com.smartparking.service;

import com.smartparking.entity.Favorite;
import com.smartparking.repository.FavoriteRepository;

public interface FavoriteService extends Service<Favorite, Long, FavoriteRepository> {

    Long getCountByClientId(Long id);

    Favorite findFavoriteByClientEmailAndParkingId(String email, Long parkingId);
}
