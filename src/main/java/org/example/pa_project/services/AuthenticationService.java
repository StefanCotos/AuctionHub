package org.example.pa_project.services;

import org.example.pa_project.entities.User;
import org.example.pa_project.repositories.UserRepository;
import org.example.pa_project.security.AuthenticationResponse;
import org.example.pa_project.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling authentication and registration.
 */
@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructs an {@code AuthenticationService} with the specified dependencies.
     *
     * @param repository            the user repository
     * @param passwordEncoder       the password encoder
     * @param jwtService            the JWT service
     * @param authenticationManager the authentication manager
     */
    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registers a new user and generates a JWT token for the user.
     *
     * @param request the user registration request
     * @return an {@code AuthenticationResponse} containing the JWT token
     */
    public AuthenticationResponse register(User request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = repository.save(user);
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    /**
     * Authenticates a user and generates a JWT token for the authenticated user.
     *
     * @param request the user authentication request
     * @return an {@code AuthenticationResponse} containing the JWT token
     */
    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = repository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }
}
