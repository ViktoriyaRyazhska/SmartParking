package com.smartparking.service;

import com.smartparking.entity.Favorite;

public interface FavoriteService {
    Favorite updateFavorite(Favorite favorite);
    void deleteFavorite(Favorite favorite);
    void saveFavorite(Favorite favorite);
}
