package com.yvolabs.projectmanagementsystemserver.repository;

import com.yvolabs.projectmanagementsystemserver.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Yvonne N
 */
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Optional<Invitation> findByToken(String token);

    Optional<Invitation> findByEmail(String userEmail);
}
