package com.tcc.easyjobgo.repository;

import org.springframework.stereotype.Repository;

import com.tcc.easyjobgo.model.Token;

@Repository
public interface ITokenRepository {
    
    Token findByToken(String token);

    Token saveToken(Token token);

    Token updateToken(Token token);

    void deleteToken(int id);
}
