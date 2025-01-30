package com.albymens.task_management.controller;

import com.albymens.task_management.entity.User;
import com.albymens.task_management.response.APIResponse;
import com.albymens.task_management.service.CustomerUserDetailsService;
import com.albymens.task_management.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerUserDetailsService.class);


    @PostMapping("/user/register")
    public ResponseEntity<APIResponse> createUser(@RequestBody @Valid User user, BindingResult result){
        logger.info("Received user details ");
        if(result.hasErrors()){
            Map<String, String> errorMessage = new HashMap<>();
            result.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                errorMessage.put(fieldName, error.getDefaultMessage());
                logger.error("Error creating user details {} {}", fieldName, error.getDefaultMessage());
            });
            return ResponseEntity.status(400).body(new APIResponse(false, null, errorMessage));
        }
        return ResponseEntity.status(201).body(userService.createUser(user));
    }
}
