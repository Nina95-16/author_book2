package com.example.demo.security;

import com.example.demo.model.Author;
import com.example.demo.repo.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
     @Autowired
    private AuthorRepo authorRepo;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Author author= authorRepo.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CurrentUser(author);
    }
}
