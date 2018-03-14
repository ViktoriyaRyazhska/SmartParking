package com.smartparking.repository;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;

@NoRepositoryBean
public abstract class AbstractRepository<T, ID> extends SimpleJpaRepository<T, ID> implements Repository<T, ID> {

    private final EntityManager entityManager;

    public AbstractRepository(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public void refresh(T entity) {
        entityManager.refresh(entity);
    }
}
