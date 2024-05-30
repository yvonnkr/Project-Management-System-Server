package com.yvolabs.projectmanagementsystemserver.exception;

import com.yvolabs.projectmanagementsystemserver.exception.custom.ObjectNotFoundException;
import com.yvolabs.projectmanagementsystemserver.exception.custom.UserAlreadyExistsException;
import io.jsonwebtoken.io.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Yvonne N
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({ObjectNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> handleObjectNotFoundException(ObjectNotFoundException ex) {
        ExceptionResponse response = ExceptionResponse.builder()
                .statusCode(404)
                .message("Not Found")
                .data(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ExceptionResponse response = ExceptionResponse.builder()
                .statusCode(400)
                .message("Bad Request")
                .data(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        ExceptionResponse response = ExceptionResponse.builder()
                .statusCode(400)
                .message("Bad Request")
                .data(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseEntity<?> handleObjectNotFoundException(Exception ex) {
        ExceptionResponse response = ExceptionResponse.builder()
                .statusCode(401)
                .message("Invalid Login Credentials")
                .data(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler({InsufficientAuthenticationException.class, AccessDeniedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseEntity<?> handleInsufficientAuthenticationException(Exception ex) {
        ExceptionResponse response = ExceptionResponse.builder()
                .statusCode(401)
                .message("Login credentials are incorrect")
                .data(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler({DecodingException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseEntity<?> handleDecodingException(DecodingException ex) {
        ExceptionResponse response = ExceptionResponse.builder()
                .statusCode(401)
                .message("Error Decoding JWT: Possible solutions maybe to remove 'Bearer' when parsing token Eg. in parseClaimsJws()")
                .data(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Fallback handles any unhandled exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<?> handleAllOtherUnhandledException(Exception ex) {
        ExceptionResponse response = ExceptionResponse.builder()
                .statusCode(500)
                .message("Internal Server Error")
                .data(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
