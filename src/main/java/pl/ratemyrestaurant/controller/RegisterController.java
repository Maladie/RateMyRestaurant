package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.service.UserService;

@RestController
public class RegisterController {

    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Info registerClient(@RequestParam String username,
                               @RequestParam String password) {
        return userService.register(username, password);
    }
    @RequestMapping(method = RequestMethod.POST, value = "/api/register")
    public Info registerToApi(@RequestParam String username,
                               @RequestParam String password) {
        return userService.register(username, password);
    }
}
