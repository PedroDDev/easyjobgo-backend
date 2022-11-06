package com.tcc.easyjobgo.repository;

import org.springframework.stereotype.Repository;

import com.tcc.easyjobgo.model.Role;

@Repository
public interface IRoleRepository {
    
    Role findRoleById(int id);
}
