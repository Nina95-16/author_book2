package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.model.Role;
import com.example.demo.security.CurrentUser;
import com.example.demo.service.AuthorService;
import com.example.demo.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final AuthorService authorService;
    @Value("${author.upload.dir}")
    private String uploadDir;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String homePage(@AuthenticationPrincipal CurrentUser principal, ModelMap modelMap) {
        String username = null;
        if (principal != null) {
            username = principal.getUsername();
        }
        modelMap.addAttribute("username", username);
        return "home";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/successLogin")
    public String successLogin(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser == null) {
            return "redirect:/";
        }
        Author author = currentUser.getAuthor();
        if (author.getRole() == Role.ADMIN) {
            return "redirect:/admin";
        } else {
            return "redirect:/user";
        }
    }

    @GetMapping("/author/save")
    public String saveAuthor(@RequestParam(value = "id", required = false) Integer id, ModelMap modelMap) {
        if (id != null) {
            modelMap.addAttribute("author", authorService.getOne(id));
        } else {
            modelMap.addAttribute("author", new Author());
        }
        return "editAuthor";
    }

    @PostMapping("/author/save")
    public String saveAuthor(@ModelAttribute Author author, @RequestParam("image") MultipartFile image) throws IOException, MessagingException {
        Optional<Author> byUsername = authorService.findByUsername(author.getUsername());
        if (byUsername.isPresent()) {
            return "redirect:/?msg=User already exists";
        }
        boolean imageExist = image.getOriginalFilename() != null && !image.getOriginalFilename().isEmpty();
        if (imageExist) {
            String photoUrl = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            File file = new File(uploadDir + File.separator + photoUrl);
            image.transferTo(file);
            author.setImageUrl(photoUrl);
        }
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        authorService.save(author, image);
        if (imageExist) {
            emailService.sendWithAttachment(author.getEmail(), "Welcome Subject",
                    "Hello " + author.getName() + " You have registered successfully!", uploadDir + File.separator + author.getImageUrl());
        } else {
//            emailService.send(author.getEmail(),"Welcome Subject",
//                    "Hello " + author.getName() + " You have registered successfully!");
            emailService.sendHtml(author.getEmail(), "Welcome Subject",
                    "Hello " + author.getName() + "<h1> You have registered successfully!</h1> <a href=\"http://localhost:8080\">Open Website</a>" );
        }
        return "redirect:/allAuthors";
    }

    @GetMapping(value = "/author/image")
    public @ResponseBody
    byte[] getImage(@RequestParam("photoUrl") String photoUrl) throws IOException {
        InputStream in = new FileInputStream(uploadDir + File.separator + photoUrl);
        return IOUtils.toByteArray(in);
    }

    @GetMapping("/allAuthors")
    public String authors(ModelMap modelMap) {
        List<Author> authors = authorService.findAll();
        modelMap.addAttribute("authors", authors);
        return "allAuthors";
    }

    @GetMapping("/author/delete")
    public String deleteAuthor(@RequestParam(value = "id", required = false) Integer id) {
        authorService.deleteAuthor(id);
        return "redirect:/allAuthors";
    }
}
