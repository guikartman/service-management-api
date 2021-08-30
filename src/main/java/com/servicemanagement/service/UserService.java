package com.servicemanagement.service;

import com.servicemanagement.domain.User;
import com.servicemanagement.dto.UserNewDTO;

public interface UserService {

    /**
     * This method should save the user into the database.
     *
     * @param userNewDTO
     * @return
     */
    User createNewUser(UserNewDTO userNewDTO);

    /**
     * This method should set a new password to an user.
     *
     * @param email
     * @param oldPassword
     * @param newPassword
     */
    void changePassword(String email, String oldPassword,String newPassword);

    /**
     * This method should generate and save a new password to an user.
     *
     * @param email
     */
    void retrievePassword(String email);

    /**
     * This method should return a user by the email parsed.
     *
     * @param email
     * @return
     */
    User findUserByEmail(String email);

}
