package com.tcc.easyjobgo.factory;

import com.tcc.easyjobgo.repository.IRoleRepository;
import com.tcc.easyjobgo.service.RoleService;

public class RoleFactory {
    
    public static IRoleRepository createRoleService()
    {
        return new RoleService();
    }
}
