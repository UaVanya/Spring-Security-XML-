package com.spilnasprava.business.dao;

import com.spilnasprava.entity.mysql.User;
import com.spilnasprava.entity.mysql.UserKey;
import com.spilnasprava.entity.postgresql.Area;

import java.util.List;

/**
 * Create DAO interface to work with the User
 */
public interface UserDAO {

    /**
     * Get user by nickname from DB.
     *
     * @return user
     */
    public User getUserByName(String nickname);

    /**
     * Get list User from database.
     *
     * @return list User
     */
    List<User> getAllUsers();

    /**
     * Save User in DB.
     *
     * @param user
     * @return id user
     */
    long addUser(User user);
    }
