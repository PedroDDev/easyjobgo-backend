package com.tcc.easyjobgo.factory;

import com.tcc.easyjobgo.repository.IServiceCategoryRepository;
import com.tcc.easyjobgo.service.ServiceCategoryService;

public class ServiceCategoryFactory {
    
    public static IServiceCategoryRepository createServiceCategoryService()
    {
        return new ServiceCategoryService();
    }
}
