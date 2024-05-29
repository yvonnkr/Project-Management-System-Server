package com.yvolabs.projectmanagementsystemserver.exception.custom;

/**
 * @author Yvonne N
 */
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String email) {
        super("Account with email " + email + " already exists");
    }
}
