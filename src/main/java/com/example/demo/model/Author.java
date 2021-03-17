package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    @Enumerated(value=EnumType.STRING)
    private Role role;
    private String bio;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String imageUrl;
}
