package pl.ratemyrestaurant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.ratemyrestaurant.model.User;

@RestController
public class LoginController {

    @PostMapping(value = "/login")
    public ResponseEntity<User> loginUser(@RequestBody() User user){
        System.out.println(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/isLoggedIn")
    public boolean isLoggedIn(){
        return false;
    }
}
