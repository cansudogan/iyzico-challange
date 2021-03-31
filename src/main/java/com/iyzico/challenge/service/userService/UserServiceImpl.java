package com.iyzico.challenge.service.userService;

import com.iyzico.challenge.entity.Basket;
import com.iyzico.challenge.entity.User;
import com.iyzico.challenge.repository.BasketRepository;
import com.iyzico.challenge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BasketRepository basketRepository;

    public UserServiceImpl(UserRepository userRepository, BasketRepository basketRepository) {
        this.userRepository = userRepository;
        this.basketRepository = basketRepository;
    }

    @Override
    public User retrieveUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
        Basket basket = new Basket();
        basket.setStatus(Basket.Status.ACTIVE);
        basket.setUser(user);
        basket.setTotal(BigDecimal.ZERO);
        basketRepository.save(basket);
    }
}
