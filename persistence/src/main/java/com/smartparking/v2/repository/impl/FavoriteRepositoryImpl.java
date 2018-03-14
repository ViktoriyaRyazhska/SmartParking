package com.smartparking.v2.repository.impl;

import com.smartparking.entity.Favorite;
import com.smartparking.v2.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class FavoriteRepositoryImpl extends AbstractRepository<Favorite, Long> {

    public FavoriteRepositoryImpl(@Autowired EntityManager entityManager) {
        super(Favorite.class, entityManager);
    }
}
