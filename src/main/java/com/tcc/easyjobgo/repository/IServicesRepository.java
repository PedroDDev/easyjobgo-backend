package com.tcc.easyjobgo.repository;

import java.util.List;
import java.util.UUID;

import com.tcc.easyjobgo.model.Services;

public interface IServicesRepository {
    
    List<Services> findAll();
    List<Services> findAllByUser();
    Services findById(UUID id);
    
    Services findByToken(String token);

    Services update(Services service);

    Services save(Services service);

    String delete(UUID id);
}
