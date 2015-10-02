package com.spilnasprava.object;

/**
 * Create enum for @see com.spilnasprava.entity.postgresql.Area.class
 */
public enum AreaType {
    AREA1(0),
    AREA2(1),
    AREA3(2);

    private int value;

    /**
     * Sets the value of this Enum as int.
     *
     * @param value
     */
    AreaType(int value) {
        this.value = value;
    }

    /**
     * @return value of this Enum as int
     */
    public int getValue() {
        return value;
    }

    /**
     * Establish value
     *
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Compares the value of the index, and returns its value.
     *
     * @param value
     * @return the value class AreaType if equal otherwise null.
     */
    public static AreaType parse(int value) {
        for (AreaType area : values()) {
            if (area.getValue() == value) {
                return area;
            }
        }
        return null;
    }
}
