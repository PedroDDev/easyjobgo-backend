package com.tcc.easyjobgo.model;

public class Day {
    
    private Integer id;
    private String description;
    
    public Day(){
        
    }
    public Day(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
