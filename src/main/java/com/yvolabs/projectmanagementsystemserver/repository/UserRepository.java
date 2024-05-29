package com.yvolabs.projectmanagementsystemserver.repository;

import com.yvolabs.projectmanagementsystemserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Yvonne N
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
