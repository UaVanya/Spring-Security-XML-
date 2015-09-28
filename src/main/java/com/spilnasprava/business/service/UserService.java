package com.spilnasprava.business.service;

import com.spilnasprava.entity.mysql.User;
import com.spilnasprava.entity.mysql.UserKey;
import com.spilnasprava.entity.postgresql.Area;

import java.util.List;

/**
 * Create Service interface to work with the User
 */
public interface UserService {

    /**
     * Get user by nickname
     *
     * @return user
     */
    public User getUserByName(String nickname);

    /**
     * Get list User from database.
     *
     * @return list User
     */
    public List<User> getAllUsers();

    /**
     * Save User in DB.
     *
     * @param user
     * @return id user
     */
    public long addUser(User user);

}
