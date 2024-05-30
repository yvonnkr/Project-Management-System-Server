package com.yvolabs.projectmanagementsystemserver.service;

import jakarta.mail.MessagingException;

/**
 * @author Yvonne N
 */
public interface EmailService {
    void sendEmailWithToken(String userEmail, String link) throws MessagingException;
}
