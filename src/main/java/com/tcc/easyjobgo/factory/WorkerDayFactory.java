package com.tcc.easyjobgo.factory;

import com.tcc.easyjobgo.repository.IWorkerDayRepository;
import com.tcc.easyjobgo.service.WorkerDayService;

public class WorkerDayFactory {
    
    public static IWorkerDayRepository createWorkerDayService(){
        return new WorkerDayService();
    }
}
