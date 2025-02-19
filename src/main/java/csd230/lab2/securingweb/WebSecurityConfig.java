package csd230.lab2.securingweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Tells Spring that this class contains configuration settings for the application
@EnableWebSecurity // Activates Spring Security’s web support, integrating it with Spring MVC
public class WebSecurityConfig {

    @Bean // Marks this method as returning a Spring Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Here we define which requests need authentication and which do not
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home").permitAll() // Let everyone access the root and home
                        .anyRequest().authenticated() // Force login for everything else
                )
                // Configure formlogin
                .formLogin((form) -> form
                        .loginPage("/login") // Show the login page on authentication
                        .permitAll() // Allow everyone to view and submit the login form
                )
                // Configure logout support
                .logout((logout) -> logout.permitAll()); // Everyone can log out
        return http.build(); // Build the security filter chain and return it
    }

    @Bean // This bean tells Spring Security how to load or create user information
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user") // The username is “user”
                .password("password") // The password is “password”
                .roles("USER") // Assigns a user role
                .build(); // Builds a user object with those properties

        // Returns an in-memory user manager that holds the user above
        return new InMemoryUserDetailsManager(user);
    }
}
