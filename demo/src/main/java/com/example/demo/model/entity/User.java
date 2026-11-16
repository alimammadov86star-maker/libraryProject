package com.example.demo.model.entity;

import com.example.demo.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = false)
    private String email;
    @Column(nullable = false)
    private Long password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
}
