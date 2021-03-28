package com.iyzico.challenge.service.userService;

import com.iyzico.challenge.entity.User;
import com.iyzico.challenge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User retrieveUserById(long id) {
       Optional<User> user = userRepository.findById(id);
       return user.get();
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }
}
