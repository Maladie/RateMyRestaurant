package pl.ratemyrestaurant.service;

import pl.ratemyrestaurant.dto.NewUserDTO;
import pl.ratemyrestaurant.model.Info;

public interface UserService {

    Info register(NewUserDTO newUserDTO);
    //TODO refresh token, logout
}
