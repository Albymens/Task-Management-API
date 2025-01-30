package com.albymens.task_management.controller;

import com.albymens.task_management.response.APIResponse;
import com.albymens.task_management.service.JwtTokenProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestHeader("username") String username,
                                             @RequestHeader("password") String password){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if (!authentication.isAuthenticated()){
            logger.error("{} login credentials failed", username);
            return ResponseEntity.status(401).body(new APIResponse(false, "Unauthorized", null));
        }

        String token = jwtTokenProvider.generateToken(authentication.getName());
        Map<String, String> data = new HashMap<>();
        data.put("token", token);

        logger.info("{} login successfully", username);
        return ResponseEntity.ok(new APIResponse(true, "Welcome!! You've Login successfully", data));
    }
}
