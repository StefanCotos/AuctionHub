package org.example.pa_project.controllers;

import org.example.pa_project.entities.User;
import org.example.pa_project.security.AuthenticationResponse;
import org.example.pa_project.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for the authentication for services
 */
@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    /**
     * A user need to register to get their JWT token for access services in application
     *
     * @param request - you need to enter your username and password
     * @return - the JWT token
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * If you already register, to get another JWT token please login
     *
     * @param request - again your username and password
     * @return - the JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}