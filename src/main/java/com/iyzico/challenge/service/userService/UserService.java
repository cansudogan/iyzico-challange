package com.iyzico.challenge.service.userService;

import com.iyzico.challenge.entity.User;

public interface UserService {

    User retrieveUserById(long id);

    void createUser(User user);
}
