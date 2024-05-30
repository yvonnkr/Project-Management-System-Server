package com.yvolabs.projectmanagementsystemserver.service;

import com.yvolabs.projectmanagementsystemserver.model.Invitation;
import jakarta.mail.MessagingException;

/**
 * @author Yvonne N
 */
public interface InvitationService {
    void sendInvitation(String email, Long projectId) throws MessagingException;

    Invitation acceptInvitation(String token, Long userId);

    String getTokenByUserMail(String userEmail);

    void deleteToken(String token);
}
