package com.yvolabs.projectmanagementsystemserver.repository;

import com.yvolabs.projectmanagementsystemserver.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yvonne N
 */
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
