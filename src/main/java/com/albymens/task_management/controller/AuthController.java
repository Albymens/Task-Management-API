package com.albymens.task_management.controller;

import com.albymens.task_management.response.APIResponse;
import com.albymens.task_management.service.JwtTokenProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication",
        description = "Endpoints for user authentication, including login and JWT token management.")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProviderService jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProviderService jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    @Operation(
            summary = "User Login",
            description = "This endpoint allows users to login, After a successful login a Jwt token is generated" +
                    "and returned. This token must be used in the header for subsequent request to access protected resources"
    )
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
