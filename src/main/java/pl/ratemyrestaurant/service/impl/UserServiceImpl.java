package pl.ratemyrestaurant.service.impl;

import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public Info register(String username, String password, String name, String surname) {
        return new Info();
    }
}
