package com.yvolabs.projectmanagementsystemserver.controller;

import com.yvolabs.projectmanagementsystemserver.config.JwtProvider;
import com.yvolabs.projectmanagementsystemserver.dto.request.LoginRequest;
import com.yvolabs.projectmanagementsystemserver.dto.response.AuthResponse;
import com.yvolabs.projectmanagementsystemserver.exception.custom.UserAlreadyExistsException;
import com.yvolabs.projectmanagementsystemserver.model.User;
import com.yvolabs.projectmanagementsystemserver.repository.UserRepository;
import com.yvolabs.projectmanagementsystemserver.service.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yvonne N
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsServiceImpl customUserDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent((foundUser) -> {
                    throw new UserAlreadyExistsException(user.getEmail());
                });

        User createdUser = User.builder()
                .fullname(user.getFullname())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();

        userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse response = AuthResponse.builder()
                .jwt(jwt)
                .message("signup success")
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse response = AuthResponse.builder()
                .jwt("Bearer " + jwt)
                .message("login success")
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("username or password is incorrect");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("username or password is incorrect");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
