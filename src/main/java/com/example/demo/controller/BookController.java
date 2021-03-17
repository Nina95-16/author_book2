package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @PostMapping("/book/save")
    public String saveBook(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/";
    }

    @GetMapping("/book/save")
    public String saveBook(@RequestParam(value = "id", required = false) Integer id, ModelMap modelMap) {
        if (id != null) {
            modelMap.addAttribute("book", bookService.getOne(id));
        } else {
            modelMap.addAttribute("book", new Book());
        }
        modelMap.addAttribute("authors",authorService.findAll());
        return "editBook";
    }

    @GetMapping("/allBooks")
    public String books(ModelMap modelMap) {
        List<Book> books = bookService.allBooks();
        modelMap.addAttribute("books", books);
        List<Author> authors = authorService.findAll();
        modelMap.addAttribute("author", authors);
        return "allBooks";
    }
    @GetMapping("/book/delete")
    public String bookDelete(@RequestParam("id") int id){
        bookService.deleteBook(id);
        return "redirect:/allBooks";
    }
}
