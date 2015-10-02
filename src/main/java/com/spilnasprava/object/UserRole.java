package com.spilnasprava.object;

/**
 * Created by VJKL on 30.09.2015.
 */
public enum UserRole {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String value;

    /**
     * Sets the value of this Enum as int.
     *
     * @param value
     */
    UserRole(String value) {
        this.value = value;
    }

    /**
     * @return value of this Enum as int
     */
    public String getValue() {
        return value;
    }

    /**
     * Establish value
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Compares the value of the index, and returns its value.
     *
     * @param value
     * @return the value class AreaType if equal otherwise null.
     */
    public static UserRole parse(String value) {
        for (UserRole role : values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        return null;
    }
}