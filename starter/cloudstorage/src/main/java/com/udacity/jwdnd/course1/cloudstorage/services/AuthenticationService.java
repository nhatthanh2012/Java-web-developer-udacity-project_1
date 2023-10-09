package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.entities.Users;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {
    private UserMapper userMapper;

    private HashService hashService;

    // constructor by ThanhTLN
    public AuthenticationService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    @Override
    // verify user login
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // comment out by ThanhTLN
        String password = authentication.getCredentials().toString();
        String username = authentication.getName();

        // get user exist
        Users user = userMapper.getUser(username);
        if (user != null) {
            String encodedSalt = user.getSalt();

            String hashedPassword = hashService.getHashedValue(password, encodedSalt);
            // if login success
            if (user.getPassword().equals(hashedPassword)) {
                // comment out by ThanhTLN
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }

        return null;
    }

    @Override
    // override by ThanhTLN
    // comment out by ThanhTLN
    public boolean supports(Class<?> authentication) {
        // compare authenticate equall
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

