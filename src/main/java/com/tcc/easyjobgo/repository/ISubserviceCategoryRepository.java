package com.tcc.easyjobgo.repository;

import java.util.List;

import com.tcc.easyjobgo.model.SubserviceCategory;

public interface ISubserviceCategoryRepository {
    
    List<SubserviceCategory> findAll();

    SubserviceCategory findById(int id);
    List<SubserviceCategory> findByDesc(String desc);

    SubserviceCategory saveSubserviceCategory(SubserviceCategory sc);

    SubserviceCategory updateSubserviceCategory(SubserviceCategory sc);

    String deleteSubserviceCategory(int id);
}