package com.albymens.task_management.service;

import com.albymens.task_management.entity.User;
import com.albymens.task_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User createUser(User user){
        if(null != user.getId()){
            Optional<User> existingUser = userRepository.findById(user.getId());
            if(existingUser.isPresent()){
                return user;
            }
        }
        return userRepository.save(user);
    }
}
