package com.yvolabs.projectmanagementsystemserver.service;

import com.yvolabs.projectmanagementsystemserver.config.JwtProvider;
import com.yvolabs.projectmanagementsystemserver.exception.custom.ObjectNotFoundException;
import com.yvolabs.projectmanagementsystemserver.model.User;
import com.yvolabs.projectmanagementsystemserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Yvonne N
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws ObjectNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("User not found with email: " + email));

    }

    @Override
    public User findUserById(Long userId) throws ObjectNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User not found with id: " + userId));

    }

    @Override
    public User updateUsersProjectSize(User user, int number) throws Exception {
        user.setProjectSize(user.getProjectSize() + number);
        return userRepository.save(user);
    }
}
