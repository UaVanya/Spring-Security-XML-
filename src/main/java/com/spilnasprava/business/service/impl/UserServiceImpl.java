package com.spilnasprava.business.service.impl;

import com.spilnasprava.business.dao.UserDAO;
import com.spilnasprava.business.service.UserService;
import com.spilnasprava.entity.mysql.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implements interface Service @see com.spilnasprava.business.service.UserService
 */
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    /**
     * Get user by nickname
     *
     * @return user
     */
    public User getUserByName(String nickname) {
        return userDAO.getUserByName(nickname);
    }

    /**
     * Get list User from database.
     *
     * @return list User
     */
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    /**
     * Save User in DB.
     *
     * @param user
     * @return id user
     */
    public long addUser(User user) {
        return userDAO.addUser(user);
    }
}
