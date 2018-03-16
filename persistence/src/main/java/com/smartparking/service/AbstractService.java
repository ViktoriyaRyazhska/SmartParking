package com.smartparking.service;

import com.smartparking.repository.Repository;

import javax.transaction.Transactional;
import java.util.List;

public abstract class AbstractService<T, ID, R extends Repository<T, ID>> implements Service<T, ID, R> {

    protected final R repository;

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
        entity.toString();
        repository.save(entity);
    }

    @Override
    public void refresh(T entity) {
        repository.refresh(entity);
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id).orElse(null);
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
