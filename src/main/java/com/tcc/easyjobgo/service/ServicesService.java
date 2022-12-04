package com.tcc.easyjobgo.service;

import java.util.List;
import java.util.UUID;

import com.tcc.easyjobgo.model.Service;
import com.tcc.easyjobgo.repository.IServicesRepository;

public class ServicesService implements IServicesRepository{

    @Override
    public List<Service> findAll() {
        return null;
    }

    @Override
    public List<Service> findAllByUser() {
        return null;
    }

    @Override
    public Service findById(UUID id) {
        return null;
    }

    @Override
    public Service updateUser(Service service) {
        return null;
    }

    @Override
    public Service saveWorkerDay(Service service) {
        return null;
    }

    @Override
    public String deleteWorkerDay(UUID id) {
        return null;
    }
    
}
