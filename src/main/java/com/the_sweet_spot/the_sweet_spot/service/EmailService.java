package com.the_sweet_spot.the_sweet_spot.service;

import jakarta.mail.MessagingException;

import java.util.HashMap;

public interface EmailService {
    void sendEmail(String sendTo, String subject, String message);

    void sendEmailWithAttachment(String sendTo, String subject, String message, String attachmentPath);

    void sendHtmlTemplateEmail(String sendTo, String subject, String templateName, HashMap<String, Object> model);
}
