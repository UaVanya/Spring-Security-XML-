package com.spilnasprava.business.dao.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
/**
 * Created by VJKL on 15.09.2015.
 * Check getter class SessionBaseInit
 */
@RunWith(MockitoJUnitRunner.class)
public class TestSessionBaseInit {

    private static Session session = null;

    @Mock
    private SessionFactory sessionFactoryMySQL;

    @Mock
    private SessionFactory sessionFactoryPostgreSQL;

    @InjectMocks
    private SessionBaseInit sessionBaseInit;

    /**
     * Check the resulting compound of the method SeesionBaseInit class
     */
    @Test
    public void testGetSessionMySQL() {
        when(sessionFactoryMySQL.getCurrentSession()).thenReturn(session);
        assertThat(sessionBaseInit.getSessionMySQL(), sameInstance(session) );
    }

    /**
     * Check the resulting compound of the method SeesionBaseInit class
     */
    @Test
    public void testGetSessionPostgreSQL() {
        when(sessionFactoryMySQL.getCurrentSession()).thenReturn(session);
        assertThat(sessionBaseInit.getSessionPostgreSQL(), sameInstance(session) );
    }
}
