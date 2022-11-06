package com.tcc.easyjobgo.model;

import java.sql.Timestamp;
import java.util.UUID;

public class Token {
    
    private Integer id;
    private String token;
    private Timestamp createDate;
    private Timestamp expiresDate;
    private Timestamp confirmationDate;
    private UUID userId;
    
    public Token(Integer id, String token, Timestamp createDate, Timestamp expiresDate, Timestamp confirmationDate, UUID userId) {
        this.id = id;
        this.token = token;
        this.createDate = createDate;
        this.expiresDate = expiresDate;
        this.confirmationDate = confirmationDate;
        this.userId = userId;
    }

    public Token(String token, Timestamp createDate, Timestamp expiresDate, UUID userId) {
        this.token = token;
        this.createDate = createDate;
        this.expiresDate = expiresDate;
        this.userId = userId;
    }

    public Token(){}

    public Integer getId() {
        return id;
    }
    
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
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
    
    public Timestamp getConfirmationDate() {
        return confirmationDate;
    }
    public void setConfirmationDate(Timestamp confirmationDate) {
        this.confirmationDate = confirmationDate;
    }
   
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
