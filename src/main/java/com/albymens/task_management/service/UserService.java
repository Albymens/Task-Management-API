package com.albymens.task_management.service;

import com.albymens.task_management.entity.User;
import com.albymens.task_management.repository.UserRepository;
import com.albymens.task_management.response.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public APIResponse createUser(User user){
       User existingUser = userRepository.findByUsername(user.getUsername());
       if(existingUser == null){
           return new APIResponse(false, "User already exist", null);
       }
        return new APIResponse(true, "User created successfully", userRepository.save(user));
    }
}
