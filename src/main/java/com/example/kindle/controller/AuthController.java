package com.example.kindle.controller;

import com.example.kindle.payload.LoginDto;
import com.example.kindle.payload.UserDto;
import com.example.kindle.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;
    @PostMapping("{addUser}")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto){
        UserDto dto=userService.addUser(userDto);
        return new ResponseEntity<>("user added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        String token=userService.verifyLogin(loginDto);
        if(token!=null){
            return new ResponseEntity<>(token,HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Credentials ",HttpStatus.UNAUTHORIZED);
    }
}
