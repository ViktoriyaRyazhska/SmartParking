package com.smartparking.service;

import com.smartparking.repository.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T, ID, R extends Repository<T, ID>> implements Service<T, ID, R> {

    private final R repository;

    protected AbstractService(R repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Override
    @Transactional
    public void save(T entity) {
        repository.save(entity);
    }

    @Override
    public void refresh(T entity) {
        repository.refresh(entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public R getRepository() {
        return repository;
    }
}
