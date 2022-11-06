package com.tcc.easyjobgo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.tcc.easyjobgo.model.User;

@Repository
public interface IUserRepository {
 
    List<User> findAll();
    User findByUsername(String username);
    User findByCpf(String cpf);
    User findById(UUID id);

    User saveUser(User user);

    User updateUser(User user);
    void updateUserStatus(UUID id, Integer status);

    String deleteUser(UUID uuid);
}
