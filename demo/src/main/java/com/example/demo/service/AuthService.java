package com.example.demo.service;

import com.example.demo.model.dto.auth.AuthResponse;
import com.example.demo.model.dto.auth.RegisterRequest;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.Role;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        String hashPass = passwordEncoder.encode(request.getPassword());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exits");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exits");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(Long.valueOf(hashPass));
        user.setRole(Role.USER);

        try {
            userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username or email already exits", ex);
        }

        return new AuthResponse("Registration is done", user.getUsername(), user.getRole().name());
    }
}
