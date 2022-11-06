package com.tcc.easyjobgo.factory;

import com.tcc.easyjobgo.repository.ITokenRepository;
import com.tcc.easyjobgo.service.TokenService;

public class TokenFactory {
    
    public static ITokenRepository createTokenService()
    {
        return new TokenService();
    }
}
