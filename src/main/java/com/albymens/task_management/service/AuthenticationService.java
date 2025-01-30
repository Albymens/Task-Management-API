package com.albymens.task_management.service;

import com.albymens.task_management.entity.User;
import com.albymens.task_management.repository.UserRepository;
import com.albymens.task_management.response.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public APIResponse authenticate(String username, String password){
        User user = userRepository.findByUsername(username);
        if(null == user || !passwordEncoder.matches(password, user.getPassword())){
            logger.error("user: {} not found, Invalid credentials", username);
           return new APIResponse(false, "Invalid username or password", null);
        }

        return new APIResponse(true, "Authentication successful", null);
    }
}
