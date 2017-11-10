package pl.ratemyrestaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.model.User;
import pl.ratemyrestaurant.repository.UserRepository;
import pl.ratemyrestaurant.service.UserService;
import pl.ratemyrestaurant.utils.SecurityUtils;
import pl.ratemyrestaurant.utils.CheckingUtils;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //TODO add validation filter before
    @Override
    public Info register(String username, String password) {
        boolean usernameEmpty = CheckingUtils.isNullOrEmpty(username);
        boolean passwordEmpty = CheckingUtils.isNullOrEmpty(password);
        Info info = new Info();
        info.setKey(UUID.randomUUID().toString());
        if(!usernameEmpty && !passwordEmpty) {
            if(userRepository.findUserByUsername(username)!= null){
                info.setCode(209L);
                info.setDesc("Registration failed! Username already used");
                info.setObject(username);
            } else {
                User user = new User();
                user.setUsername(username);
                String passSalt = SecurityUtils.generateSalt();
                user.setSalt(passSalt);
                String passHash = SecurityUtils.generatePasswordHash(password, passSalt);
                user.setPassword(passHash);
                userRepository.save(user);
                info.setDesc("Registration successful!");
            }
        }else {
            if(usernameEmpty){
                info.setCode(201L);
                info.setDesc("Registration failed! Username invalid or empty");
                info.setObject(username);
            } else {
                info.setCode(202L);
                info.setDesc("Registration failed! Password invalid or empty");
                info.setObject(password);
            }
        }
        return info;
    }
}
