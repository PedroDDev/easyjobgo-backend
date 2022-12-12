package com.tcc.easyjobgo.repository;

import java.util.List;
import java.util.UUID;

import com.tcc.easyjobgo.model.Services;

public interface IServicesRepository {
    
    List<Services> findAll();
    List<Services> findAllByUser();
    Services findById(UUID id);
    List<Services> findAllByClient(UUID serviceClient);
    List<Services> findAllByWorker(UUID serviceWorker);
    
    Integer count(UUID serviceWorker);
    
    Services findByToken(String token);

    Services update(Services service);
    Services updateWorkerStatus(Services service);
    Services updateStartDate(Services service);

    Services save(Services service);

    String delete(UUID id);
}
