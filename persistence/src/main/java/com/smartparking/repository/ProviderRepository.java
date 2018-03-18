package com.smartparking.repository;

import com.smartparking.entity.Provider;
import com.smartparking.model.filter.ProviderFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProviderRepository extends JpaRepository<Provider, Long> {

}