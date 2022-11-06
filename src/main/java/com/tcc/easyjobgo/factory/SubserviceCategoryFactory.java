package com.tcc.easyjobgo.factory;

import com.tcc.easyjobgo.repository.ISubserviceCategoryRepository;
import com.tcc.easyjobgo.service.SubserviceCategoryService;

public class SubserviceCategoryFactory {
    
    public static ISubserviceCategoryRepository createSubserviceCategoryService()
    {
        return new SubserviceCategoryService();
    }
}
