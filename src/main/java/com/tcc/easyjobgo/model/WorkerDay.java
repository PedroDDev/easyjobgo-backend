package com.tcc.easyjobgo.model;

import java.util.UUID;

public class WorkerDay {
    
    private Integer id;
    private UUID workerId;
    private Integer dayId;
    private Integer status;
    
    public WorkerDay(){
        
    }
    public WorkerDay(Integer id, UUID workerId, Integer dayId, Integer status) {
        this.id = id;
        this.workerId = workerId;
        this.dayId = dayId;
        this.status = status;
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

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

}
