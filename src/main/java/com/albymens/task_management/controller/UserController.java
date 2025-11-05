package com.albymens.task_management.controller;

import com.albymens.task_management.entity.User;
import com.albymens.task_management.response.APIResponse;
import com.albymens.task_management.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User Registration", description = "Endpoints for user registration. After successful registration, login using your credentials to receive a JWT token.")
public class UserController {
    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/user/register")
    @Operation(
            summary = "Register a new user.",
            description = "This endpoint allows new users to register by providing their details such as username, and password. " +
                    " Successful registration generates a response with user information and a confirmation of the registration."
    )
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
