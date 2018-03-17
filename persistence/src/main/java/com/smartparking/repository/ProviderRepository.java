package com.smartparking.repository;

import com.smartparking.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProviderRepository extends JpaRepository<Provider, Long> {

}