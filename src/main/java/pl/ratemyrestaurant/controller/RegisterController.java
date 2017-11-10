package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.service.UserService;

import java.util.HashMap;

@RestController
public class RegisterController {

    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/*", method = RequestMethod.OPTIONS)
    public String test(){
        return "Option request method not implemented";
    }

    @PostMapping(value = "/register")
    public Info registerClient(@RequestBody HashMap<String, String> formParams) {
        return userService.register(formParams.get("username"), formParams.get("password"));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/register")
    public Info registerToApi(@RequestParam String username,
                               @RequestParam String password) {
        return userService.register(username, password);
    }
}
