package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final HashService hashService;
    private final UserMapper userMapper;

    // constructor for hashService, userMapper
    // comment out by ThanhTLN
    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    // sign up new user
    // comment out by ThanhTLN
    public int signupNewUser(Users users) {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        // random byte
        random.nextBytes(salt);
        String strEncodedSalt = Base64.getEncoder().encodeToString(salt);
        String strHashedPassword = hashService.getHashedValue(users.getPassword(), strEncodedSalt);

        // save to DB
        Users newUser  = new Users();
        newUser.setUsername(users.getUsername());
        newUser.setSalt(strEncodedSalt);
        newUser.setPassword(strHashedPassword);
        newUser.setFirstName(users.getFirstName());
        newUser.setLastName(users.getLastName());

        return userMapper.insert(newUser);
    }

    // check user exist
    public boolean isUserExistInDb(String username) {
        // user not exist
        return userMapper.getUser(username) == null;
    }

    // comment out by ThanhTLN
    public Users getUserInformation() {
        // get info user login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users userLogin = userMapper.getUser(userName);

        return userLogin;
    }
}
