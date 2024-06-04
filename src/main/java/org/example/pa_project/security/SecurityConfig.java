package org.example.pa_project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up security configurations.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImp userDetailsServiceImp;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Constructs a {@code SecurityConfig} with the specified user details service and JWT authentication filter.
     *
     * @param userDetailsServiceImp   the user details service implementation
     * @param jwtAuthenticationFilter the JWT authentication filter
     */
    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures the security filter chain.
     *
     * @param http the {@code HttpSecurity} to modify
     * @return the configured {@code SecurityFilterChain}
     * @throws Exception if an error occurs while configuring
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println(http);
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req.requestMatchers("/login/**", "/register/**", "/auctions/**", "/users/**", "/bids/**", "/imagesDatabase/**")
                                .permitAll()
                                .requestMatchers(
                                        "/v1/api/get-token",
                                        "/swagger-ui.html",
                                        "/swagger-ui/*",
                                        "/v3/api-docs/**",
                                        "/swagger-resources/**",
                                        "/webjars/**").permitAll()
                                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/swagger-ui", "/swagger-ui.html/**", "/v3/api-docs/**", "/v3/api-docs")
                                .permitAll()
                                .requestMatchers("/", "/about.html", "/admin.html", "/auction-info.html", "/auction-ma.html/**", "/auctions.html/**", "/contact.html", "/forget-up.html", "/help.html", "/my-auctions.html", "/login.html", "/signup.html")
                                .permitAll()
                                .requestMatchers("/auctionsForm/**")
                                .permitAll()
                                .requestMatchers("/logger/**","/resources/**", "/styles/**", "/images/**", "/scripts/**", "/ws/**", "/callGetAuctions")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(userDetailsServiceImp)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
//    req -> req.requestMatchers("/login/**", "/register/**")
//            .permitAll()
//                                .anyRequest()
//                                .authenticated()


//                                    .requestMatchers("/login/**", "/register/**").permitAll()
//                                .anyRequest()
//                                .authenticated()
//                                .requestMatchers("/auctions").permitAll()

    /**
     * Provides a {@code PasswordEncoder} bean that uses BCrypt for encoding passwords.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides an {@code AuthenticationManager} bean.
     *
     * @param configuration the authentication configuration
     * @return the authentication manager
     * @throws Exception if an error occurs while configuring
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
