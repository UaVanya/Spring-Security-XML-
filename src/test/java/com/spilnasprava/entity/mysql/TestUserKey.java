package com.spilnasprava.entity.mysql;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by VJKL on 15.09.2015.
 * Checks the setter/getter values of this class entity
 */
public class TestUserKey {
    private UserKey userKey = new UserKey();

    /**
     * Check the correctly established 'id'
     */
    @Test
    public void testGetAndSetId() {
        long id = 1;
        userKey.setId(id);
        assertThat(userKey.getId(), is(id));
    }

    /**
     * Check the correctly established 'key'
     */
    @Test
    public void testGetAndSetKey() {
        String key = "KeyTest";
        userKey.setKey(key);
        assertThat(userKey.getKey(), is(key));
    }

    /**
     * Check the correctly established 'user_id'
     */
    @Test
    public void testGetAndSetUser() {
        User user = new User();
        long userId = 1;
        user.setId(userId);
        userKey.setUser(user);
        assertThat(userKey.getUser(), is(user));
    }
}
