package com.tcc.easyjobgo.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

public class Services {
    
    private UUID id;
    private Time initalHour;
    private Time finalHour;
    private String description;
    private Object value;
    private Timestamp createDate;
    private Timestamp expiresDate;
    private boolean confirmationClient;
    private boolean confirmationWorker;
    private Timestamp startDate;
    private Timestamp endDate;
    private String endToken;
    private boolean endConfirmationClient;
    private boolean endConfirmationWorker;
    private Integer dayServiceWorker;
    private UUID serviceClient;
    private UUID serviceWorker;
    private String firstNameClient;
    private String lastNameClient;
    private String firstNameWorker;
    private String lastNameWorker;
    
    public Services(){
        
    }
    public Services(UUID id, Time initalHour, Time finalHour, String description, Object value, Timestamp createDate,
            Timestamp expiresDate, boolean confirmationClient, boolean confirmationWorker, Timestamp startDate, 
            Timestamp endDate, String endToken, boolean endConfirmationClient, boolean endConfirmationWorker, 
            Integer dayServiceWorker, UUID serviceClient, UUID serviceWorker, String firstNameClient, 
            String lastNameClient, String firstNameWorker, String lastNameWorker) {
        
        this.id = id;
        this.initalHour = initalHour;
        this.finalHour = finalHour;
        this.description = description;
        this.value = value;
        this.createDate = createDate;
        this.expiresDate = expiresDate;
        this.confirmationClient = confirmationClient;
        this.confirmationWorker = confirmationWorker;
        this.endDate = endDate;
        this.endToken = endToken;
        this.endConfirmationClient = endConfirmationClient;
        this.endConfirmationWorker = endConfirmationWorker;
        this.dayServiceWorker = dayServiceWorker;
        this.serviceClient = serviceClient;
        this.serviceWorker = serviceWorker;
        this.firstNameClient = firstNameClient;
        this.lastNameClient = lastNameClient;
        this.firstNameWorker = firstNameWorker;
        this.lastNameWorker = lastNameWorker;

    }

    public UUID getId() {
        return id;
    }

    public Time getInitalHour() {
        return initalHour;
    }
    public void setInitalHour(Time initalHour) {
        this.initalHour = initalHour;
    }
    
    public Time getFinalHour() {
        return finalHour;
    }
    public void setFinalHour(Time finalHour) {
        this.finalHour = finalHour;
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    
    public Timestamp getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    
    public Timestamp getExpiresDate() {
        return expiresDate;
    }
    public void setExpiresDate(Timestamp expiresDate) {
        this.expiresDate = expiresDate;
    }
    
    public boolean isConfirmationClient() {
        return confirmationClient;
    }
    public void setConfirmationClient(boolean confirmationClient) {
        this.confirmationClient = confirmationClient;
    }
    
    public boolean isConfirmationWorker() {
        return confirmationWorker;
    }
    public void setConfirmationWorker(boolean confirmationWorker) {
        this.confirmationWorker = confirmationWorker;
    }
    
    public Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }
 
    public Timestamp getEndDate() {
        return endDate;
    }
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
    
    public String getEndToken() {
        return endToken;
    }
    public void setEndToken(String endToken) {
        this.endToken = endToken;
    }
    
    public boolean isEndConfirmationClient() {
        return endConfirmationClient;
    }
    public void setEndConfirmationClient(boolean endConfirmationClient) {
        this.endConfirmationClient = endConfirmationClient;
    }
    
    public boolean isEndConfirmationWorker() {
        return endConfirmationWorker;
    }
    public void setEndConfirmationWorker(boolean endConfirmationWorker) {
        this.endConfirmationWorker = endConfirmationWorker;
    }
    
    public Integer getDayServiceWorker() {
        return dayServiceWorker;
    }
    public void setDayServiceWorker(Integer dayServiceWorker) {
        this.dayServiceWorker = dayServiceWorker;
    }
    
    public UUID getServiceClient() {
        return serviceClient;
    }
    public void setServiceClient(UUID serviceClient) {
        this.serviceClient = serviceClient;
    }
    
    public UUID getServiceWorker() {
        return serviceWorker;
    }
    public void setServiceWorker(UUID serviceWorker) {
        this.serviceWorker = serviceWorker;
    }
    public String getFirstNameClient() {
        return firstNameClient;
    }
    public void setFirstNameClient(String firstNameClient) {
        this.firstNameClient = firstNameClient;
    }
    public String getLastNameClient() {
        return lastNameClient;
    }
    public void setLastNameClient(String lastNameClient) {
        this.lastNameClient = lastNameClient;
    }
    public String getFirstNameWorker() {
        return firstNameWorker;
    }
    public void setFirstNameWorker(String firstNameWorker) {
        this.firstNameWorker = firstNameWorker;
    }
    public String getLastNameWorker() {
        return lastNameWorker;
    }
    public void setLastNameWorker(String lastNameWorker) {
        this.lastNameWorker = lastNameWorker;
    }
 
    

    
}
