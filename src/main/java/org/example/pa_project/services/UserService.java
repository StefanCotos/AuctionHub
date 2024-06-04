package org.example.pa_project.services;

import org.example.pa_project.entities.User;
import org.example.pa_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructs a new UserService with the specified UserRepository.
     *
     * @param userRepository the user repository
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user
     * @return the user with the specified username, or null if not found
     */
    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return the user with the specified ID, or null if not found
     */
    public User getUser(long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves a user by their username.
     * (Note: This method is redundant and the same as {@link #getUserByUserName(String)}).
     *
     * @param username the username of the user
     * @return the user with the specified username, or null if not found
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    /**
     * Adds a new user.
     *
     * @param user the user to add
     * @return the added user
     */
    public User addUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
