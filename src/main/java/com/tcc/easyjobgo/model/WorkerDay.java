package com.tcc.easyjobgo.model;

import java.util.UUID;

public class WorkerDay {
    
    private Integer id;
    private UUID workerId;
    private Integer dayId;
    
    public WorkerDay(){
        
    }
    public WorkerDay(Integer id, UUID workerId, Integer dayId) {
        this.id = id;
        this.workerId = workerId;
        this.dayId = dayId;
    }

    public Integer getId() {
        return id;
    }

    public UUID getWorkerId() {
        return workerId;
    }
    public void setWorkerId(UUID workerId) {
        this.workerId = workerId;
    }
    
    public Integer getDayId() {
        return dayId;
    }
    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }
}
