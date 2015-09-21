package com.spilnasprava.entity.mysql;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Create entity for table user_key with MySQL DB.
 */
@Entity
@Table(name = "user_key")
public class UserKey implements Serializable {
    private long id;
    private String key;
    @Column(name = "user_id")
    private User user;

    /**
     * @return current user_id the userKey
     */
    @Id
    @Column(name = "id_user_key")
    @GeneratedValue
    public long getId() {
        return id;
    }

    /**
     * Sets the id the userKey
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return current key the userKey
     */
    @Column(name = "key_id")
    public String getKey() {
        return key;
    }

    /**
     * Sets the key the area
     *
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return current user_id the userKey
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    /**
     * Sets the area_id the area
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
