package com.albymens.task_management.controller;

import com.albymens.task_management.response.APIResponse;
import com.albymens.task_management.service.JwtTokenProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProviderService jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestHeader("username") String username,
                                             @RequestHeader("password") String password){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        String token = jwtTokenProvider.generateToken(authentication.getName());
        Map<String, String> data = new HashMap<>();
        data.put("token", token);

        return ResponseEntity.ok(new APIResponse(true, "Login successfully!!!", data));
    }
}
