package com.example.demo.config;

import com.example.demo.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/successLogin")
                //.successForwardUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET, "/book/save/").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/book/delete/").hasAnyAuthority("ADMIN")
                .antMatchers("/author/save/").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/author/delete").hasAnyAuthority("ADMIN");
        //.anyRequest().authenticated();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("poxos")
//                .password(passwordEncoder.encode("poxos"))
//                .roles("USER").and()
//                .withUser("petros")
//                .password(passwordEncoder.encode("petros"))
//                .roles("ADMIN");
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}