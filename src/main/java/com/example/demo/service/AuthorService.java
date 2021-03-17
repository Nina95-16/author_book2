package com.example.demo.service;

import com.example.demo.model.Author;
import com.example.demo.repo.AuthorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepo authorRepo;
    public Optional<Author> findByUsername(String username){
        return authorRepo.findByUsername(username);
    }

    public void save(Author author, MultipartFile image) {
        authorRepo.save(author);
    }

    public List<Author> findAll() {
        return authorRepo.findAll();
    }

    public Author getOne(int id) {
        return authorRepo.getOne(id);
    }

    public void deleteAuthor(int id) {
        authorRepo.deleteById(id);
    }
}
