package com.tcc.easyjobgo.model;

public class SubserviceCategory {
    
    private Integer id;

    private String description;
    private Integer serviceCategoryId;
    
    public SubserviceCategory(){

    }
    public SubserviceCategory(Integer id, String description, Integer serviceCategoryId){
        this.id = id;
        this.description = description;
        this.serviceCategoryId = serviceCategoryId;
    }

    public Integer getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public Integer getServiceCategoryId(){
        return serviceCategoryId;
    }
    public void setServiceCategoryId(Integer serviceCategoryId){
        this.serviceCategoryId = serviceCategoryId;
    }
}
