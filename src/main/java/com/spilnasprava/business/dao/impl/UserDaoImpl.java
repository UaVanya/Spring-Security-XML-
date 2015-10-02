package com.spilnasprava.business.dao.impl;

import com.spilnasprava.business.dao.utils.SessionBaseInit;
import com.spilnasprava.business.dao.UserDAO;
import com.spilnasprava.entity.mysql.User;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Implements interface DAO @see com.spilnasprava.business.dao.UserDAO
 */
public class UserDaoImpl extends SessionBaseInit implements UserDAO {
    private final static Logger logger = Logger.getLogger(UserDaoImpl.class);

    /**
     * Get user by nickname
     *
     * @return user
     */
    public User getUserByName(String nickname) {
        logger.info("Run get list entity User by 'nickname' from DB");
        List listObject = getSessionMySQL().createCriteria(User.class).add(Restrictions.eq("nickname", nickname)).list();
        User user = null;
        if (!listObject.isEmpty()) {
            logger.info("Run pull User with list User");
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
        logger.info("Run get list all Users from DB");
        return (List<User>) getSessionMySQL().createCriteria(User.class).list();
    }

    /**
     * Save User in DB.
     *
     * @param user
     * @return id user
     */
    public long addUser(User user) {
        logger.info("Run save User entity in DB");
        return (Long) getSessionMySQL().save(user);
    }
}
