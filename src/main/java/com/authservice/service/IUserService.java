package com.authservice.service;

import com.authservice.model.User;

public interface IUserService {

    User findOne(String username);

}
