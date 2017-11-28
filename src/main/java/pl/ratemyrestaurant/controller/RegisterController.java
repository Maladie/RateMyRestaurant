package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.NewUserDTO;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.service.UserService;
import pl.ratemyrestaurant.type.APIInfoCodes;


@RestController
public class RegisterController {

    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Info>registerClient(@RequestBody NewUserDTO newUserDTO) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Info register = userService.register(newUserDTO);
        if(register.getInfoCode() == APIInfoCodes.OK) {
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(register, status);
    }
}
