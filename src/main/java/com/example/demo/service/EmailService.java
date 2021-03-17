package com.example.demo.service;

import javax.mail.MessagingException;

public interface EmailService {
    void send(String to, String subject, String text);

    void sendWithAttachment(String to, String subject, String text, String filePath) throws MessagingException;

    void sendHtml(String to, String subject, String htmlContent) throws MessagingException;
}
