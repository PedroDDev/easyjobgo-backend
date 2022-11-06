package com.tcc.easyjobgo.util.email;

public interface IEmailSender {
    
    void send(String to, String subject, String email);
}
