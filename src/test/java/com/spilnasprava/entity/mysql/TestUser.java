package com.spilnasprava.entity.mysql;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by VJKL on 15.09.2015.
 * Checks the setter/getter values of this class entity
 */
public class TestUser {

    private User user = new User();

    /**
     * Check the correctly established 'id'
     */
    @Test
    public void testGetAndSetId() {
        long id = 1;
        user.setId(id);
        assertThat(user.getId(), is(id));
    }

    /**
     * Check the correctly established 'nickname'
     */
    @Test
    public void testGetAndSetNickname() {
        String nickname = "NicknameTest";
        user.setName(nickname);
        assertThat(user.getName(), is(nickname));
    }

    /**
     * Check the correctly established 'password'
     */
    @Test
    public void testGetAndSetPassword() {
        String password = "PasswordTest";
        user.setName(password);
        assertThat(user.getName(), is(password));
    }

    /**
     * Check the correctly established 'name'
     */
    @Test
    public void testGetAndSetName() {
        String name = "NameTest";
        user.setName(name);
        assertThat(user.getName(), is(name));
    }

    /**
     * Check the correctly established 'email'
     */
    @Test
    public void testGetAndSetEmail() {
        String email = "EmailTest";
        user.setEmail(email);
        assertThat(user.getEmail(), is(email));
    }

    /**
     * Check the correctly established 'user_id'
     */
    @Test
    public void testGetAndSetUserKey() {
        UserKey userKey = new UserKey();
        long userId = 1;
        user.setId(userId);
        userKey.setUser(user);
        user.setUserKey(userKey);
        assertThat(user.getUserKey(), is(userKey));
    }
}