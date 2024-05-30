package com.yvolabs.projectmanagementsystemserver.service;

import com.yvolabs.projectmanagementsystemserver.exception.custom.ObjectNotFoundException;
import com.yvolabs.projectmanagementsystemserver.model.Invitation;
import com.yvolabs.projectmanagementsystemserver.repository.InvitationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Yvonne N
 */
@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final EmailService emailService;

    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {
        String invitationToken = UUID.randomUUID().toString();

        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);

        invitationRepository.save(invitation);

        String invitationLink = "http://localhost:5173/accept_invitation?token=" + invitationToken; // when clicked will redirect frontend
        emailService.sendEmailWithToken(email, invitationLink);

    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) {
        return invitationRepository.findByToken(token)
                .orElseThrow(() -> new ObjectNotFoundException("Invalid Invitation token, Invitation with token " + token + " not found"));
    }

    @Override
    public String getTokenByUserMail(String userEmail) {
        Invitation invitation = invitationRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ObjectNotFoundException("Invitation with email " + userEmail + " not found"));

        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        invitationRepository.findByToken(token)
                .ifPresent(invitationRepository::delete);

    }
}
