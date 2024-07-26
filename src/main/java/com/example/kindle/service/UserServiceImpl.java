package com.example.kindle.service;

import com.example.kindle.model.User;
import com.example.kindle.payload.LoginDto;
import com.example.kindle.payload.UserDto;
import com.example.kindle.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto addUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.findByUsername(userDto.getUsername());

        if (existingUser.isPresent()) {
            logger.warn("Username {} already exists", userDto.getUsername());
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User savedUser = userRepository.save(user);

        UserDto resultDto = new UserDto();
        resultDto.setId(savedUser.getId());
        resultDto.setUsername(savedUser.getUsername());
        // Consider not returning the password
        return resultDto;
    }

    @Override
    public String verifyLogin(LoginDto loginDto) {
         Optional<User> opUser =userRepository.findByUsername(loginDto.getUsername());
        if(opUser.isPresent()) {
            User user=opUser.get();
            if(BCrypt.checkpw(loginDto.getPassword(),user.getPassword())){
                return jwtService.generateToken(user);
            }

        }
         return null;
    }
}
