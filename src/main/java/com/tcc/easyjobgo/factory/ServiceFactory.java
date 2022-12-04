package com.tcc.easyjobgo.factory;

import com.tcc.easyjobgo.repository.IServicesRepository;
import com.tcc.easyjobgo.service.ServicesService;

public class ServiceFactory {
    
    public static IServicesRepository createServicesService(){
        return new ServicesService();
    }
}
