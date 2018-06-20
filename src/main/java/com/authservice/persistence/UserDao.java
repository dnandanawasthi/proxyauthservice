package com.authservice.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.authservice.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
