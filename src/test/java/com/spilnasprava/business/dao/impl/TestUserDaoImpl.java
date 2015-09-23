package com.spilnasprava.business.dao.impl;

import com.spilnasprava.business.dao.UserDAO;
import com.spilnasprava.business.dao.utils.SessionBaseInit;
import com.spilnasprava.entity.mysql.User;
import com.spilnasprava.entity.mysql.UserKey;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VJKL on 15.09.2015.
 * Tests methods class UserDaoImpl
 */
@RunWith(MockitoJUnitRunner.class)
public class TestUserDaoImpl {
    private static User user = new User();
    private static UserKey userKey = new UserKey();

    @Mock
    private Session session;

    @Mock
    private SessionBaseInit sessionBaseInit;

    @InjectMocks
    private UserDaoImpl userDao;

    @BeforeClass
    public static void init() {
        user.setId(1l);
        user.setName("NameTest");
        user.setEmail("EmailTest");
        userKey.setId(1l);
        userKey.setKey("key-test");
        userKey.setUser(user);
        user.setUserKey(userKey);
    }


    @Test
    public void testGetUserByName() {
//        List userList = new ArrayList<User>();
//        userList.add(user);
//        when(userDao.getSessionMySQL()).thenReturn(session);
//        when(userDao.getSessionMySQL().createCriteria(User.class).add(Restrictions.eq("nickname", user.getNickname())).list()).thenReturn(userList);
//
//        assertThat(user, is(userDao.getUserByName(user.getNickname())));
    }

    @Test
    public void testGetAllUser() {
//        List<User> userList = new ArrayList<>();
//        userList.add(user);
//        when(userDao.getAllUsers()).thenReturn(userList);

//        when(userDao.getSessionMySQL().save(user)).thenReturn(1l);
//        assertThat(userList, is(userDao.getAllUsers()));
    }

    @Test
    public void testAddUser() {
//        long userId = 1l;
//        when(userDao.addUser(user)).thenReturn(userId);

//        when(userDao.getSessionMySQL()).thenReturn(session);
//        when(userDao.getSessionMySQL()
//                .save(user))
//                .thenReturn(1l);
//        assertThat(userId, is(userDao.addUser(user)));
    }
}