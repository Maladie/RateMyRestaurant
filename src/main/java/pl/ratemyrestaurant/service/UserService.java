package pl.ratemyrestaurant.service;

import pl.ratemyrestaurant.model.Info;

public interface UserService {

    Info register(String username, String password);
}
