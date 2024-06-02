package org.example.pa_project.security;

/**
 * A class representing an authentication response which contains a token.
 */
public class AuthenticationResponse {
    private final String token;

    /**
     * Constructs an {@code AuthenticationResponse} with the specified token.
     *
     * @param token the authentication token
     */
    public AuthenticationResponse(String token) {
        this.token = token;
    }

    /**
     * Returns the authentication token.
     *
     * @return the authentication token
     */
    public String getToken() {
        return token;
    }
}
