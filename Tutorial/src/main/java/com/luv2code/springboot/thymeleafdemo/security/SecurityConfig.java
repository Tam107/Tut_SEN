package com.luv2code.springboot.thymeleafdemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // In-memory user details with roles
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        // Define in-memory users with roles
        UserDetails john = User.builder()
                .username("john")
                .password("{noop}test123")  // {noop} means no password encoding
                .roles("EMPLOYEE")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(john, mary, susan);
    }

    // Configure security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Authorize HTTP requests based on roles
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/employees/list").hasRole("EMPLOYEE")  // Employees can view the list
                        .requestMatchers("/leaders/**").hasRole("MANAGER")  // Only managers can access leader routes
                        .requestMatchers("/systems/**").hasRole("ADMIN")    // Only admins can access system routes
                        .anyRequest().authenticated()  // All other requests require authentication
                )

                // Configure custom login page

                .formLogin(form -> form
                        .loginPage("/employees/showMyLoginPage")  // Custom login page
                        .loginProcessingUrl("/authenticateTheUser")  // Where the login form is submitted to
                        .defaultSuccessUrl("/employees/list", true)  // Redirect to the employee list after login
                        .permitAll()
                )


                // Configure logout support
                .logout(logout -> logout
                        .logoutUrl("/employees/logout")  // Specify the logout URL
                        .logoutSuccessUrl("/employees/showMyLoginPage?logout")  // Redirect to login page after logout
                        .permitAll()
                )

                // Configure access denied page
                .exceptionHandling(configurer -> configurer
                        .accessDeniedPage("/employees/access-denied")  // Custom access denied page
                )

                // Updated CSRF handling: enabled by default, no need to explicitly configure
                .csrf(csrf -> csrf.disable());  // Disable CSRF if necessary, depending on the requirements of your app

        return http.build();
    }
}
