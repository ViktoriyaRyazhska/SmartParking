package com.smartparking.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Service<T, ID, R extends JpaRepository<T, ID>> {
    void delete(T entity);

    void save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    R getRepository();
}
