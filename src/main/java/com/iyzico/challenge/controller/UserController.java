package com.iyzico.challenge.controller;

import com.iyzico.challenge.entity.CreateUserRequest;
import com.iyzico.challenge.entity.User;
import com.iyzico.challenge.service.userService.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@EnableAutoConfiguration
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public String createUser(@RequestBody CreateUserRequest createUserRequest) {

        logger.info("New user created ");
        try {
            User user = new User(createUserRequest.getName(), createUserRequest.getSurname(), createUserRequest.getIdentityNo()
                    , createUserRequest.getCity(), createUserRequest.getCountry(), createUserRequest.getEmail(), createUserRequest.getPhoneNumber(),
                    createUserRequest.getAddress(), createUserRequest.getZipCode(), LocalDateTime.now());
            userService.createUser(user);
            return "Added user with id: " + user.getUserId();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/retrieveUserById/{id}", method = RequestMethod.GET)
    public String retrieveUserById(@PathVariable long id) {
        logger.info("Getting user with id " + id);
        try {
            User user = userService.retrieveUserById(id);
            return user.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
