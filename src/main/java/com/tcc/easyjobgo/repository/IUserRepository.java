package com.tcc.easyjobgo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.tcc.easyjobgo.model.User;

@Repository
public interface IUserRepository {
 
    List<User> findAll();
    List<User> findAll(String username);
    User findByUsername(String username);
    User findByCpf(String cpf);
    User findById(UUID id);

    List<User> findByDesc(String username, String desc);


    User saveUser(User user);

    User updateUser(User user);
    void updateUserStatus(UUID id, Integer status);

    String deleteUser(UUID uuid);
}
