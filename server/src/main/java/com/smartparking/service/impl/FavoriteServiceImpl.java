package com.smartparking.service.impl;

import com.smartparking.entity.Favorite;
import com.smartparking.service.FavoriteService;

import javax.transaction.Transactional;

public class FavoriteServiceImpl implements FavoriteService {

    @Override
    @Transactional
    public Favorite updateFavorite(Favorite favorite) {
        return null;
    }

    @Override
    @Transactional
    public void deleteFavorite(Favorite favorite) {

    }

    @Override
    @Transactional
    public void saveFavorite(Favorite favorite) {

    }
}
