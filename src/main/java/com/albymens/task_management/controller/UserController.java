package com.albymens.task_management.controller;

import com.albymens.task_management.entity.User;
import com.albymens.task_management.response.APIResponse;
import com.albymens.task_management.service.UserService;
import jakarta.validation.Valid;
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

    @PostMapping("/user/add")
    public ResponseEntity<APIResponse> createUser(@RequestBody @Valid User user, BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errorMessage = new HashMap<>();
            result.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                errorMessage.put(fieldName, error.getDefaultMessage());
            });
            return ResponseEntity.status(400).body(new APIResponse(false, null, errorMessage));
        }
        return ResponseEntity.status(202).body(userService.createUser(user));
    }
}
