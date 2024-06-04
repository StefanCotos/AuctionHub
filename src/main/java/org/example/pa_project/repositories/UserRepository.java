package org.example.pa_project.repositories;

import org.example.pa_project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User by their username.
     *
     * @param username the username of the user
     * @return an Optional containing the User with the specified username, or an empty Optional if not found
     */
    Optional<User> findByUsername(String username);
}
