package com.tcc.easyjobgo.factory;

import com.tcc.easyjobgo.repository.IUserRepository;
import com.tcc.easyjobgo.service.UserService;

public class UserFactory {
    
    public static IUserRepository createUserService()
    {
        return new UserService();
    }
}
