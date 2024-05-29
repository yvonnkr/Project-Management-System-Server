package com.yvolabs.projectmanagementsystemserver.dto.request;

import lombok.Data;

/**
 * @author Yvonne N
 */
@Data
public class LoginRequest {
    private String email;
    private String password;
}
