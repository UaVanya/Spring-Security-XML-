package com.spilnasprava.business.dao.impl;

import com.spilnasprava.business.dao.utils.SessionBaseInit;
import com.spilnasprava.business.dao.UserDAO;
import com.spilnasprava.entity.mysql.User;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Implements interface DAO @see com.spilnasprava.business.dao.UserDAO
 */
public class UserDaoImpl extends SessionBaseInit implements UserDAO {

    /**
     * Get user by nickname
     *
     * @return user
     */
    public User getUserByName(String nickname) {
        List listObject = getSessionMySQL().createCriteria(User.class).add(Restrictions.eq("nickname", nickname)).list();
        User user = null;
        if (!listObject.isEmpty()) {
            user = (User) listObject.get(0);
        }
        return user;
    }

    /**
     * Get list User from database.
     *
     * @return list User
     */
    public List<User> getAllUsers() {
        return (List<User>) getSessionMySQL().createCriteria(User.class).list();
    }

    /**
     * Save User in DB.
     *
     * @param user
     * @return id user
     */
    public long addUser(User user) {
        return (Long) getSessionMySQL().save(user);
    }
}
