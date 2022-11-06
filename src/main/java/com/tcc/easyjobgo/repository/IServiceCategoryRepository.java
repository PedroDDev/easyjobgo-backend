package com.tcc.easyjobgo.repository;

import java.util.List;

import com.tcc.easyjobgo.model.ServiceCategory;

public interface IServiceCategoryRepository {
    
    List<ServiceCategory> findAll();

    ServiceCategory findById(int id);
    List<ServiceCategory> findByDesc(String desc);

    ServiceCategory saveServiceCategory(ServiceCategory sc);

    ServiceCategory updateServiceCategory(ServiceCategory sc);

    String deleteServiceCategory(int id);
}
