package com.tcc.easyjobgo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tcc.easyjobgo.model.Day;

@Repository
public interface IDayRepository {
    
    List<Day> findAll();
    
    Day findDayById(int id);

}
