package com.example.kindle.service;

import com.example.kindle.payload.LoginDto;
import com.example.kindle.payload.UserDto;
import org.springframework.stereotype.Service;

public interface UserService {
    public UserDto addUser(UserDto userDto);

    String verifyLogin(LoginDto loginDto);
}
