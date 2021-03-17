package com.example.demo.service;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repo.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepo;

    public void save(Book book) {
        bookRepo.save(book);
    }

    public List<Book> allBooks() {
        return bookRepo.findAll();
    }

    public Book getOne(int id) {
        return bookRepo.getOne(id);
    }

    public void deleteBook(int id) {
        bookRepo.deleteById(id);
    }
}
