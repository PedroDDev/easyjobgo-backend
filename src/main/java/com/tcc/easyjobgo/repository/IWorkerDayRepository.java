package com.tcc.easyjobgo.repository;

import java.util.List;
import java.util.UUID;

import com.tcc.easyjobgo.model.WorkerDay;

public interface IWorkerDayRepository {
   
    List<WorkerDay> findAll();
    WorkerDay findById(Integer id);
    List<WorkerDay> findByWorkerId(UUID id);

    WorkerDay save(WorkerDay workerDay);

    void updateStatus(Integer id, Integer status);

    String delete(Integer id);
}
