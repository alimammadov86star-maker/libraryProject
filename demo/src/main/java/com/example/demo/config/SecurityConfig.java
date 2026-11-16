package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/login").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/books/", "/api/authors/")
                        .hasAnyRole("USER", "ADMIN")

                        .requestMatchers("/api/books/**", "/api/authors/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }


//    @Bean
//    UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        UserDetails user = User.builder()
//                .username("12345")
//                .password(encoder.encode("64"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("Admin_1")
//                .password(encoder.encode("!@$Vhtjgiuh(h]]jdkfb}<>0252353*^%&*8*%%2356685456615biegygf$%&%^"))
//                .roles("Admin")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}