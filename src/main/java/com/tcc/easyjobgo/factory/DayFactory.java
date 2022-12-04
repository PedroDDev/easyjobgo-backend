package com.tcc.easyjobgo.factory;

import com.tcc.easyjobgo.repository.IDayRepository;
import com.tcc.easyjobgo.service.DayService;

public class DayFactory {
    
    public static IDayRepository createDayService(){
        return new DayService();
    }
}
