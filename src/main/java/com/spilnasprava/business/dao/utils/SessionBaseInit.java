package com.spilnasprava.business.dao.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Creates a SessionFactory
 */
public class SessionBaseInit {
    @Autowired
    @Qualifier("sessionFactoryMySQL")
    private SessionFactory sessionFactoryMySQL;

    @Autowired
    @Qualifier("sessionFactoryPostgreSQL")
    private SessionFactory sessionFactoryPostgreSQL;

    /**
     * @return Session for MySQL
     */
    public Session getSessionMySQL() {
        return sessionFactoryMySQL.getCurrentSession();
    }

    /**
     * @return Session for PostgreSQL
     */
    public Session getSessionPostgreSQL() {
        return sessionFactoryPostgreSQL.getCurrentSession();
    }
}
