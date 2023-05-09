package com.example.OnlineAuctionApp.service;

import com.example.OnlineAuctionApp.controllers.model.LoginBody;
import com.example.OnlineAuctionApp.controllers.model.RegisterBody;
import com.example.OnlineAuctionApp.exceptions.UserAlreadyExistsException;
import com.example.OnlineAuctionApp.models.User;
import com.example.OnlineAuctionApp.repositories.UserRepository;
import com.example.OnlineAuctionApp.security.EncryptionService;
import com.example.OnlineAuctionApp.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;

    public Optional<User> getUserById(Long id){
        return userRepository.getUserById(id);
    }
    public User register(RegisterBody registerBody) throws UserAlreadyExistsException {
        if(userRepository.findByEmailIgnoreCase(registerBody.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        User user = new User();
        user.setFirstname(registerBody.getFirstname());
        user.setLastname(registerBody.getLastname());
        user.setEmail(registerBody.getEmail());
        user.setPhoneNumber(registerBody.getPhoneNumber());
        user.setPassword(encryptionService.encryptPassword(registerBody.getPassword()));
        return userRepository.save(user);
    }

    public String loginEmail(LoginBody loginBody){
        Optional<User> opUser = userRepository.findByEmailIgnoreCase(loginBody.getEmail());
        if(opUser.isPresent()){
            User user = opUser.get();
            if(encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())){
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }
}
