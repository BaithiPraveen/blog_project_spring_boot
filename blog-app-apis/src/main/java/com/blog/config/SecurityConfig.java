package com.blog.config;

import com.blog.security.JwtAuthenticationEntryPoint;
import com.blog.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//
@Configuration
@EnableMethodSecurity
public class SecurityConfig{
    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    /**
     * @return The BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     * @return The AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configures the security filter chain for the application.
     *
     * @param http The HttpSecurity object to configure security settings.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an exception occurs during configuration.
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (authz) ->
                        {
                            authz.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                            authz.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll();
                            authz.requestMatchers(HttpMethod.GET, "/users/**").hasAnyRole("ADMIN", "USER");
                            authz.requestMatchers(HttpMethod.POST, "/users/**").hasAnyRole("ADMIN", "USER");
                            authz.requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN");
                            authz.requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN");
                            authz.requestMatchers(HttpMethod.GET, "/post/**").hasAnyRole("ADMIN", "USER");
                            authz.anyRequest().authenticated();
                        }
                ).exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public InMemoryUserDetailsManager configInMemoryUserDetailsManager()
//    {
//
//        UserDetails admin = User.builder().username("vijay").password(passwordEncoder().encode("vijay")).roles("ADMIN").build();
//        UserDetails user1 = User.builder().username("praveen").password(passwordEncoder().encode("praveen")).roles("USER").build();
//        UserDetails user2 = User.builder().username("ajay").password(passwordEncoder().encode("ajay")).roles("USER").build();
//
//        return new InMemoryUserDetailsManager(admin,user1,user2);
//    }

}
