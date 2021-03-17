package com.example.demo.repo;

import com.example.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepo extends JpaRepository<Author, Integer> {
    Optional<Author> findByUsername(String username);
}
