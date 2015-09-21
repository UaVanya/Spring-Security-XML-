package com.spilnasprava.entity.mysql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Create entity for table user with MySQL DB.
 */
@Entity
@Table(name = "user")
public class User implements Serializable {
    private long id;
    private String nickname;
    private String password;
    private String name;
    private String email;
    private UserKey userKey;

    /**
     * @return current id the user
     */
    @Id
    @Column(name = "user_id")
    @GeneratedValue
    public long getId() {
        return id;
    }

    /**
     * Sets the id the user
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return current nickname the user
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the nickname the user
     *
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return current password the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password the user
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return current name the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name the user
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return current email the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email the user
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return current user_id the user
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserKey getUserKey() {
        return userKey;
    }

    /**
     * Sets the user_id the user
     *
     * @param userKey
     */
    public void setUserKey(UserKey userKey) {
        this.userKey = userKey;
    }
}
