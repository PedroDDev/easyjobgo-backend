package com.tcc.easyjobgo.repository;

import java.util.List;
import java.util.UUID;

import com.tcc.easyjobgo.model.Service;

public interface IServicesRepository {
    
    List<Service> findAll();
    List<Service> findAllByUser();
    Service findById(UUID id);

    Service updateUser(Service service);

    Service saveWorkerDay(Service service);

    String deleteWorkerDay(UUID id);
}
