package org.example.pa_project.security;

import org.example.pa_project.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class for loading user-specific data.
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository repository;

    /**
     * Constructs a {@code UserDetailsServiceImp} with the specified user repository.
     *
     * @param repository the user repository
     */
    public UserDetailsServiceImp(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Loads the user by their username.
     *
     * @param username the username of the user to load
     * @return the {@code UserDetails} of the user
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
