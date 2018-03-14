package com.smartparking.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository<T, ID> extends JpaRepository<T, ID> {
    void refresh(T entity);
}
