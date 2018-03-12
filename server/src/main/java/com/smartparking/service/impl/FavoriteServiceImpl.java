package com.smartparking.service.impl;

import com.smartparking.entity.Favorite;
import com.smartparking.repository.FavoriteRepository;
import com.smartparking.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Override
    @Transactional
    public Favorite updateFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @Override
    @Transactional
    public void deleteFavorite(Favorite favorite) {
        favoriteRepository.delete(favorite);
    }

    @Override
    @Transactional
    public void saveFavorite(Favorite favorite) {
        favoriteRepository.save(favorite);
    }
}
