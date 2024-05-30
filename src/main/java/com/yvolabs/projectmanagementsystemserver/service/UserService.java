package com.yvolabs.projectmanagementsystemserver.service;

import com.yvolabs.projectmanagementsystemserver.model.User;

/**
 * @author Yvonne N
 */
public interface UserService {

    User findUserProfileByJwt(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;

    User findUserById(Long userId) throws Exception;

    User updateUsersProjectSize(User user, int number) throws Exception;

}

