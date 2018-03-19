package com.smartparking.service.impl;

import com.smartparking.entity.Address;
import com.smartparking.entity.Provider;
import com.smartparking.model.filter.ProviderFilter;
import com.smartparking.model.request.ProviderRequest;
import com.smartparking.repository.AddressRepository;
import com.smartparking.repository.ProviderRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProviderServiceImpl extends AbstractService<Provider, Long, ProviderRepository> implements ProviderService {

    @Autowired
    private AddressRepository addressRepository;

    protected ProviderServiceImpl(@Autowired ProviderRepository repository) {
        super(repository);
    }

    @Override
    @Transactional
    public void saveFromRequest(ProviderRequest providerRequest) {
        Provider provider = new Provider();
        Address address = new Address();
        address.setState(providerRequest.getState());
        address.setCity(providerRequest.getCity());
        address.setStreet(providerRequest.getStreet());
        address.setBuildingNumber(providerRequest.getBuildingNumber());
        addressRepository.save(address);

        provider.setName(providerRequest.getName());
        provider.setActive(true);
        provider.setLegalAddress(address);
        getRepository().save(provider);
    }

    @Override
    public Provider changeState(Long id) {
        Provider provider = findById(id);
        provider.setActive(!provider.getActive());
        getRepository().save(provider);
        return provider;
    }

    @Override
    public Provider findProviderByClientId(Long id) {
        return getRepository().findProviderByClientId(id);
    }

    @Override
    public List<Provider> findAllByFilter(ProviderFilter providerFilter) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Provider> criteria = criteriaBuilder.createQuery(Provider.class);
        Root<Provider> provider = criteria.from(Provider.class);
        criteria.select(provider);
        List<Predicate> predicates = new ArrayList<>();
        if (providerFilter.getActive().equals("true") || providerFilter.getActive().equals("false")) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(provider.get("active"), Boolean.valueOf(providerFilter.getActive()))));
        }
        if (!providerFilter.getCompanyName().equals("")) {
            predicates.add((criteriaBuilder.and(criteriaBuilder.like(provider.get("name"), providerFilter.getCompanyName() + "%"))));
        }
        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        return getEntityManager().createQuery(criteria).getResultList();
    }

}
