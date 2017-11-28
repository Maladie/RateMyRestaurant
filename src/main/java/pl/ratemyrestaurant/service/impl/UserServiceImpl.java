package pl.ratemyrestaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.NewUserDTO;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.model.User;
import pl.ratemyrestaurant.repository.UserRepository;
import pl.ratemyrestaurant.service.UserService;
import pl.ratemyrestaurant.utils.CheckingUtils;
import pl.ratemyrestaurant.utils.SecurityUtils;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //TODO add validation filter before
    //TODO change numeric response code to enums
    @Override
    public Info register(NewUserDTO newUserDTO) {
        boolean usernameEmpty = CheckingUtils.isNullOrEmpty(newUserDTO.getUsername());
        boolean passwordEmpty = CheckingUtils.isNullOrEmpty(newUserDTO.getPassword());
        Info info = new Info();
        info.setKey(UUID.randomUUID().toString());
        if(!usernameEmpty && !passwordEmpty) {
            if(userRepository.findByUsernameIgnoreCase(newUserDTO.getUsername())!= null){
                info.setCode(209L);
                info.setDesc("Registration failed! Username already used");
                info.setObject(newUserDTO.getUsername());
            } else {
                User user = new User();
                user.setUsername(newUserDTO.getUsername());
                String passSalt = SecurityUtils.generateSalt();
                user.setSalt(passSalt);
                String passHash = SecurityUtils.generatePasswordHash(newUserDTO.getPassword(), passSalt);
                user.setPassword(passHash);
                userRepository.save(user);
                info.setDesc("Registration successful!");
            }
        }else {
            if(usernameEmpty){
                info.setCode(201L);
                info.setDesc("Registration failed! Username invalid or empty");
                info.setObject(newUserDTO.getUsername());
            } else {
                info.setCode(202L);
                info.setDesc("Registration failed! Password invalid or empty");
                info.setObject(newUserDTO.getPassword());
            }
        }
        return info;
    }
}
