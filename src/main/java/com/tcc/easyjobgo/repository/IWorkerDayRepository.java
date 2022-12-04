package com.tcc.easyjobgo.repository;

import java.util.List;
import java.util.UUID;

import com.tcc.easyjobgo.model.WorkerDay;

public interface IWorkerDayRepository {
   
    List<WorkerDay> findAll();
    WorkerDay findById(Integer id);
    List<WorkerDay> findByWorkerId(UUID id);

    WorkerDay saveWorkerDay(WorkerDay user);

    String deleteWorkerDay(Integer id);
}
