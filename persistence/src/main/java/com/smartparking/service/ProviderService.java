package com.smartparking.service;

import com.smartparking.entity.Provider;
import com.smartparking.model.filter.ProviderFilter;
import com.smartparking.model.request.ProviderRequest;
import com.smartparking.model.request.ProviderStatisticRequest;
import com.smartparking.model.response.ProviderResponse;
import com.smartparking.repository.ProviderRepository;

import java.util.List;
import java.util.Optional;

public interface ProviderService extends Service<Provider, Long, ProviderRepository> {
    void saveFromRequest(ProviderRequest providerRequest);

    Provider changeState(Long id);

    List<Provider> findAllByFilter(ProviderFilter providerFilter);

    Provider findProviderByClientId(Long id);

    Optional<Provider> findByParkingId(Long parkingId);

    ProviderStatisticRequest getStatistic();

    Optional<ProviderResponse> findByIdResponse(Long id);
}
